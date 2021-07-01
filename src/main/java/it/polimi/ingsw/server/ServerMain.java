package it.polimi.ingsw.server;

import it.polimi.ingsw.server.view.VirtualView;
import it.polimi.ingsw.server.controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerMain {

    private static final String PORT_ARGUMENT = "-port";
    private static final String HELP_ARGUMENT = "-help";
    private static final String DEMO_ARGUMENT = "-demo";
    private static final int DEFAULT_PORT = 1334;
    private static final int MIN_PORT = 1024;
    private static final int MAX_PORT = 65535;
    private static final Logger LOGGER = Logger.getLogger(ServerMain.class.getName());

    private static int portNumber;
    private Lobby lobby;

    public ServerMain(int port) {
        ServerMain.portNumber = port;
    }

    public void startServer(Boolean demo) {
        lobby = new Lobby(demo);
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }
        ServerMain.LOGGER.info("Server ready");
        Socket clientSocket;
        ServerMain.LOGGER.info("Accepting..");
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                ServerMain.LOGGER.info("Accepted client. Address: " + clientSocket.getInetAddress().getHostAddress() + " PortName: " + clientSocket.getPort());
                executor.submit(new VirtualView(clientSocket, lobby));
            } catch (IOException e) {
                break;
            }
        }
        executor.shutdown();
    }

    public static void main(String[] args) {

        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        boolean demo = false;

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
        if(arguments.contains(DEMO_ARGUMENT))
            demo = true;

        System.out.println("Server started!");

        ServerMain echoServer = new ServerMain(portNumber);
        echoServer.startServer(demo);
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }
}