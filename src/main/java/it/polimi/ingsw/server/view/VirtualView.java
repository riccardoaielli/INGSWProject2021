package it.polimi.ingsw.server.view;
import com.google.gson.Gson;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.messagesToClient.DisconnectedUpdate;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.common.messages.messagesToClient.ErrorMessage;
import it.polimi.ingsw.common.messages.messagesToServer.MessageToServer;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class VirtualView implements Runnable,View {
    private Socket socket;
    private Controller controller;
    private InetAddress clientAddress;
    private int clientPort;
    private PrintWriter out;
    private BufferedReader in;
    private Gson gson;
    private static final MessageDeserializer messageDeserializer = new MessageDeserializer();
    private String nickname;


    public VirtualView(Socket socket, Controller controller) {
        this.controller = controller;
        this.socket = socket;
        clientAddress = socket.getInetAddress();
        clientPort = socket.getPort();
        gson = new Gson();
        controller.addObserver(this);
        nickname = null;
    }

    public boolean checkIsTheCorrectVirtualView(InetAddress clientAddress,int clientPort){

        return false;
    }

    public void run() {
        System.out.println("Thread created");

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            controller.newConnection(this);
            // Leggo e scrivo nella connessione finche' non ricevo "quit"
            while (true) {
                String line = in.readLine();
                if (line.equals("quit")) { //todo capire come gestire la disconnessione
                    System.out.println("quit ricevuto, esco dal while");
                    break;
                } else {
                    System.out.println("Received: " + line);
                    MessageToServer messageToServer = messageDeserializer.deserializeMessage(line);
                    if (nickname!= null){
                        if(!nickname.equals(messageToServer.getNickname())){
                            this.update(new ErrorMessage(nickname, "This message cannot be sent by this client"));
                            //TODO fare l'enum per questo messaggio
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
            // Chiudo gli stream e il socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            if(nickname == null){
                System.err.println(e.getMessage());
                System.out.println("Stream di rete terminato");
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException | NullPointerException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                System.err.println(e.getMessage());
                System.out.println("Stream di rete terminato");
                controller.removeObserver(this);
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException | NullPointerException ioException) {
                    ioException.printStackTrace();
                }
                controller.notifyObservers(new DisconnectedUpdate(nickname));
                //controller.notifyObservers(new ); // creare messaggio di disconnessione client
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            System.out.println("stream di rete terminato");
        }
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Sends message to client
     *
     * @param message Message notified by {@link Observable}
     */
    @Override
    public void update(MessageToClient message) {
        out.println(gson.toJson(message));
        System.out.println("Sent:" + gson.toJson(message));
    }

    /**
     * Enable pinger between server and client's sockets.
     * @param enable set this argument to true to enable the pinger, set it to false to disable
     *
     */
    /*public void enablePinger(boolean enable) {
        if (enabled) {
            pinger.scheduleAtFixedRate(() -> sendMessage(new PingMessage()), 0, 1000, TimeUnit.MILLISECONDS);
        } else {
            pinger.shutdownNow();
        }
    }*/

}
