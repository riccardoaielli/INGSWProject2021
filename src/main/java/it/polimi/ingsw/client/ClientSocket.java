package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.common.messages.messagesToServer.MessageToServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is only for online mode and manage the connection to the server using sockets
 */
public class ClientSocket implements MessageSender {

    private Gson gson = new Gson();
    private PrintWriter out;
    private ScheduledExecutorService pinger;

    private String SocketInReaderLine;
    private Socket socket;
    private ClientView clientView;
    private final String PING = "ping";

    private MessageToClientDeserializer messageToClientDeserializer = new MessageToClientDeserializer();

    /**
     * Connect the server via socket and creates a thread of SocketInReader.
     */
    public ClientSocket(String hostAddress, int portNumber, ClientView clientView) {

        this.clientView = clientView;
        try {
            socket = new Socket(hostAddress, portNumber);
            //Stream to write to and send to the server
            out = new PrintWriter(socket.getOutputStream(), true);
            Thread inputThread = new Thread(new SocketInReader(socket, this));
            inputThread.start();
            enableHeartbeat();
        } catch (UnknownHostException e) {
            clientView.closeGame("Don't know about host, please insert a valid host " + hostAddress);
        } catch (IOException e) {
            clientView.closeGame("Couldn't connect to " +
                    hostAddress);
        }
    }

    /**
     * Receive a message from the SocketInReader and update the clientView.
     */
    public void readMessage(String line) {
        MessageToClient message;
        this.SocketInReaderLine = line;
        message = messageToClientDeserializer.deserializeMessage(line);
        clientView.update(message);
    }

    /**
     * Sends a message to the server via socket.
     * @param message is the message to be sent.
     */
    @Override
    public void sendMessage(MessageToServer message) {
        //Serializes and sends to socket
        out.println(gson.toJson(message));
    }

    /**
     * Disconnect the socket from the server.
     */
    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                clientView.closeGame("Could not reach server, you will be disconnected");
            }
        } catch (IOException e) {
            clientView.closeGame("Could not disconnect.");
        }
    }

    /**
     * Enable heartbeat between server and client sockets.
     */
    private void enableHeartbeat() {
        this.pinger = Executors.newSingleThreadScheduledExecutor();
        pinger.scheduleAtFixedRate(() -> out.println(PING), 0, 1000, TimeUnit.MILLISECONDS);
    }
}
