package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.server.ServerMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String SERVER_ARGUMENT = "-morServer";
    private static final String CLIENT_ARGUMENT = "-morClient";
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 1334;
    private static final int MIN_PORT = 1024;
    private static final int MAX_PORT = 65535;



    public static void main(String[] args) {
        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        if (arguments.contains(SERVER_ARGUMENT)){
            ServerMain server = new ServerMain();
            server.start(args);
        }
        else if (arguments.contains(CLIENT_ARGUMENT)){
            ClientMain.start(args);
            } else {
            String helpString = "INSERT -morClient to start the client or INSERT -morServer to start the server \n\n" +
                    "SERVER HELP:\n" +
                    "The default server port is " + DEFAULT_PORT + "\n\n" +
                    "Here is a list of all the available commands:\n" +
                    "-port: followed by the desired port number between " + MIN_PORT + " and " + MAX_PORT + " as argument\n" +
                    "-help: to get help\n\n" +
                    "CLIENT HELP:\n" +
                    "The default server host is " + DEFAULT_HOST + "\n" +
                    "The default server port is " + DEFAULT_PORT + "\n\n" +
                    "Here is a list of all the available commands:\n" +
                    "-host: followed by the desired server host ip address\n" +
                    "-port: followed by the desired server port number between " + MIN_PORT + " and " + MAX_PORT + " as argument\n" +
                    "-cli: to start the client in command line interface mode\n" +
                    "-help: to get help\n";
            System.out.println(helpString);
            return;
        }
    }
}
