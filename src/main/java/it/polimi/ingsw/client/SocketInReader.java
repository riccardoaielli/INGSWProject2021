package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * This class reads from the socket input stream of the client
 */
public class SocketInReader implements Runnable {

    String line = "";
    Socket socket;
    BufferedReader in;
    ClientSocket clientSocket;

    public SocketInReader(Socket socket, ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
        this.socket = socket;
    }

    /**
     * Receives message lines from the Server and sends it to the ClientSocket.
     */
    @Override
    public void run() {

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//stream da cui leggere dal server
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while ((line = in.readLine()) != null) {
                //System.out.println("Read this line from socket: " + line);
                clientSocket.readMessage(line);
            }
        } catch (IOException ex) {
            //ex.printStackTrace();
            System.out.println("Could not reach server");
        }

    }
}