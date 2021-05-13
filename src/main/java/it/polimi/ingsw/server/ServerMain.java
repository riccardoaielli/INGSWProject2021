package it.polimi.ingsw.server;

import it.polimi.ingsw.server.View.VirtualView;
import it.polimi.ingsw.server.controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    private static final String PORT_ARGUMENT = "-port";
    private static final String HELP_ARGUMENT = "-help";
    private static final int DEFAULT_PORT = 1334;
    private static final int MIN_PORT = 1024;
    private static final int MAX_PORT = 65535;

    private static int portNumber;
    private Controller controller;

    public ServerMain(int port) {
        ServerMain.portNumber = port;
    }

    public void startServer() {

        controller = new Controller();
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }
        System.out.println("Server ready");
        Socket clientSocket;

        while (true) {
            System.out.println("Accepting..");
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted \nclientAddress: " + clientSocket.getInetAddress() + " portName: " + clientSocket.getPort());
                System.out.println("Creating thread..");
                executor.submit(new VirtualView(clientSocket, controller));
            } catch (IOException e) {
                break;
            }
        }
        executor.shutdown();
    }

    public static void main(String[] args) {

        List<String> arguments = new ArrayList<>(Arrays.asList(args));

        if(arguments.size() == 1 && arguments.contains(HELP_ARGUMENT)) {
            String helpString = "The default server port is " + DEFAULT_PORT + "\n\n" +
                    "Here is a list of all the available commands:\n" +
                    "-port: followed by the desired port number between " + MIN_PORT + " and " + MAX_PORT + " as argument\n" +
                    "-help: to get help\n";
            System.out.println(helpString);
            return;
        }
        if(arguments.contains(PORT_ARGUMENT)){
            try {
                portNumber = Integer.parseInt(args[arguments.indexOf(PORT_ARGUMENT) + 1]);
            }catch(NumberFormatException numberFormatException) {
                System.out.println("Invalid port argument, insert " + HELP_ARGUMENT + " to see correct instruction.");
                return;
            }
            if (!(portNumber >= MIN_PORT && portNumber <= MAX_PORT)) {
                System.out.println("Invalid port number, insert " + HELP_ARGUMENT + " to see correct instruction.");
                return;
            }
        }else{
            portNumber = DEFAULT_PORT;
        }

        System.out.println("Server started!");

        ServerMain echoServer = new ServerMain(portNumber);
        echoServer.startServer();
    }
}