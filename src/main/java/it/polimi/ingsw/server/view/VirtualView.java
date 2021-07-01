package it.polimi.ingsw.server.view;
import com.google.gson.Gson;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.messages.messagesToClient.DisconnectedUpdate;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.common.messages.messagesToClient.ErrorMessage;
import it.polimi.ingsw.common.messages.messagesToServer.MessageToServer;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.ObservableGameEnder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * This class handles the connection to the client, server side
 */
public class VirtualView implements Runnable,View {
    private Socket socket;
    private Controller controller;
    private InetAddress clientAddress;
    private int clientPort;
    private PrintWriter out;
    private BufferedReader in;
    private Gson gson;
    private static final MessageToServerDeserializer messageDeserializer = new MessageToServerDeserializer();
    private String nickname;
    private Lobby lobby;
    private String line = "";
    private final int TIMEOUT_TIME = 10000;
    private static final Logger LOGGER = Logger.getLogger(VirtualView.class.getName());


    /**
     * Constructor of the virtual view, after a client has requested a connection
     * @param socket The socket used to communicate to client
     * @param lobby The lobby to manage a waiting list of players
     */
    public VirtualView(Socket socket, Lobby lobby) {
        this.controller = null;
        this.lobby = lobby;
        this.socket = socket;
        clientAddress = socket.getInetAddress();
        clientPort = socket.getPort();
        gson = new Gson();
        nickname = null;
    }

    public void run() {
        try {
            socket.setSoTimeout(TIMEOUT_TIME);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            controller = lobby.getAvailableMatch(this);
            if(controller != null){
                controller.newConnection(this);
            }
            else{
                this.update(new ErrorMessage(null, "A player is choosing the number of players. Wait..."));
            }

            while ((line = in.readLine()) != null) {
                MessageToServer messageToServer = messageDeserializer.deserializeMessage(line);
                if(!(messageToServer.getMessageType() == MessageType.PING)){
                    synchronized (this){
                        if(controller == null){
                            this.update(new ErrorMessage(null, "The first connected player is choosing the number of players. Wait..."));
                        }
                        else{
                            System.out.println("Received: " + line);
                            if (nickname!= null){
                                if(!nickname.equals(messageToServer.getNickname())){
                                    this.update(new ErrorMessage(nickname, "This message cannot be sent by this client"));
                                }
                                else{
                                    messageToServer.handleMessage(controller,this);
                                }
                            }
                            else{
                                messageToServer.handleMessage(controller,this);
                            }
                        }
                    }
                }
            }
            disconnect();
        } catch (IOException e) {
            disconnect();
        }
    }

    //Method used to manage the disconnection of a client
    private void disconnect(){
        String disconnectionMessage = "Client disconnected. Address: " + socket.getInetAddress().getHostAddress() + " Port:" + socket.getPort();
        if (nickname == null){
            disconnectionMessage = disconnectionMessage + " Nickname: not yet set";
        }
        else disconnectionMessage = disconnectionMessage + " Nickname:" + nickname;

        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Could not disconnect");
        }
        //if a controller is assigned the player is inside a match
        if(controller!= null){
            lobby.advanceQueue();
            controller.removeObserver(this);
            controller.notifyObservers(new DisconnectedUpdate(nickname));
        }
        //else the player was waiting and is removed from the queue
        else lobby.removeFromQueue(this);
        LOGGER.info(disconnectionMessage);
    }

    /**
     * Setter for nickname
     * @param nickname The nickname to assign to the virtual view, after the client has chosen one
     */
    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Sends message to client
     * @param message Message notified by {@link ObservableGameEnder}
     */
    @Override
    public void update(MessageToClient message) {
        out.println(gson.toJson(message));
        System.out.println("Sent:" + gson.toJson(message));
    }

    /**
     * Sets the controller of the match after the player was put in a waiting list in Lobby
     * @param controller controller to set
     */
    public void setController(Controller controller){
        synchronized (this){
            this.controller = controller;
            controller.newConnection(this);
        }
    }
}
