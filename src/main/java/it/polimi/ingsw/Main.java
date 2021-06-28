package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.server.ServerMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String SERVER_ARGUMENT = "-morServer";
    private static final String CLIENT_ARGUMENT = "-morClient";
    private static final String DEMO_ARGUMENT = "-demo";
    private static final String PORT_ARGUMENT = "-port";
    private static final String HELP_ARGUMENT = "-help";
    private static final String HOST_ARGUMENT = "-host";
    private static final String CLI_ARGUMENT = "-cli";
    private static final String DEFAULT_HOST = "127.0.0.1";



    public static void main(String[] args) {
        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        if (arguments.contains(SERVER_ARGUMENT)){
            ServerMain.main(args);
        }
        if (arguments.contains(CLIENT_ARGUMENT)){
            ClientMain.main(args);
        }
    }
}
