package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ClientMain {

    private static final String HOST_ARGUMENT = "-host";
    private static final String CLI_ARGUMENT = "-cli";
    private static final String PORT_ARGUMENT = "-port";
    private static final String HELP_ARGUMENT = "-help";
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 1334;
    private static final int MIN_PORT = 1024;
    private static final int MAX_PORT = 65535;

    private static int portNumber;
    private static String hostAddress;

    private final static Gson gson = new Gson();

    public static void main(String[] args) {

        List<String> arguments = new ArrayList<>(Arrays.asList(args));

        boolean cliMode = false;

        if(arguments.contains(HELP_ARGUMENT)) {
            String helpString = "The default server host is " + DEFAULT_HOST + "\n" +
                    "The default server port is " + DEFAULT_PORT + "\n\n" +
                    "Here is a list of all the available commands:\n" +
                    "-host: followed by the desired server host ip address\n" +
                    "-port: followed by the desired server port number between " + MIN_PORT + " and " + MAX_PORT + " as argument\n" +
                    "-cli: to start the client in command line interface mode\n" +
                    "-help: to get help\n";
            System.out.println(helpString);
            return;
        }
        if(arguments.contains(HOST_ARGUMENT)) {
            hostAddress = args[arguments.indexOf(HOST_ARGUMENT) + 1];
        }else{
            hostAddress = DEFAULT_HOST;
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
        if(arguments.contains(CLI_ARGUMENT)){
            System.out.println("Cli mode selected");
            cliMode = true;
            //avvia cli
        }

        if(!cliMode){
            System.out.println("Default gui mode");
            //avvia gui
        }

        try (
                Socket clientSocket = new Socket(hostAddress, portNumber);
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println(in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host, please insert a valid host " + hostAddress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostAddress);
            System.exit(1);
        }
    }
}