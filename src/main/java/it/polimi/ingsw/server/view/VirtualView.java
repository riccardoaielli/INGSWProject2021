package it.polimi.ingsw.server.view;
import com.google.gson.Gson;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Observable;

import java.io.IOException;
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
    private Gson gson;
    private static final MessageDeserializer messageDeserializer = new MessageDeserializer();

    public VirtualView(Socket socket, Controller controller) {
        this.controller = controller;
        this.socket = socket;
        clientAddress = socket.getInetAddress();
        clientPort = socket.getPort();
        gson = new Gson();
        controller.addObserver(this);
    }

    public boolean checkIsTheCorrectVirtualView(InetAddress clientAddress,int clientPort){

        return false;
    }

    public void run() {
        System.out.println("Thread created");

        try {
            Scanner in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            controller.newConnection(this);
            // Leggo e scrivo nella connessione finche' non ricevo "quit"
            while (true) {
                String line = in.nextLine();
                if (line.equals("quit")) {
                    break;
                } else {
                    System.out.println("Received: " + line);
                    messageDeserializer.deserializeMessage(line).handleMessage(controller,this);
                }
            }
            // Chiudo gli stream e il socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Sends message to client
     *
     * @param message Message notified by {@link Observable}
     */
    @Override
    public void update(MessageToClient message) {
        out.println(gson.toJson(message));
    }

}
