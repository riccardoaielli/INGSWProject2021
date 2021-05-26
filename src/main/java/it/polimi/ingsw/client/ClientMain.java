package it.polimi.ingsw.client;

import it.polimi.ingsw.client.GUI.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is the client App
 */
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

    /**
     * Main method parses the arguments and starts the CLI or GUI
     */
    public static void main(String[] args) {

        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        boolean cliMode = false;
        ClientView clientView;

        //Parsing argomenti
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
            System.out.println("CLI mode selected");
            cliMode = true;
        }else{
            System.out.println("Default gui mode");
        }
        //End parsing

        if(cliMode){
            //avvia cli
            clientView = new CLI(hostAddress, portNumber);
        }else{
            //avvia gui
            clientView = new GUI();
        }
        clientView.start();
        System.out.println("fine main");
    }
}