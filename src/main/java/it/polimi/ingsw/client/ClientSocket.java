package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.common.messages.messagesToServer.MessageToServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class is only for online mode and manage the connection to the server using sockets
 */
public class ClientSocket implements MessageSender {

    private Gson gson = new Gson();
    private PrintWriter out;

    private String SocketInReaderLine;
    private Socket socket;
    private ClientView clientView;

    private MessageToClientDeserializer messageToClientDeserializer = new MessageToClientDeserializer();

    /**
     * Connect the server via socket and creates a thread of SocketInReader.
     */
    public ClientSocket(String hostAddress, int portNumber, ClientView clientView) {

        this.clientView = clientView;

        try {
            socket = new Socket(hostAddress, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);//stream su cui scrivere per mandare al server

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host, please insert a valid host " + hostAddress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostAddress);
            System.exit(1);
        }

        Thread inputThread = new Thread(new SocketInReader(socket, this));
        inputThread.start();
        //Thread.currentThread().interrupt();
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
        //serializza e invia sul socket
        out.println(gson.toJson(message));
    }

    /**
     * Disconnect the socket from the server.
     */
    /*public void disconnect() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Could not disconnect.");
        }
    }*/
}
