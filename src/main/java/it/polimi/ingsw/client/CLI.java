package it.polimi.ingsw.client;

import it.polimi.ingsw.common.*;
import it.polimi.ingsw.server.model.Observable;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * This class is the ClientView
 */
public class CLI implements ClientView {

    private static String stdInLine;
    MessageSender messageSender;
    String hostAddress;
    int portNumber;

    /**
     * This constructor creates the CLI and calls setMessageSender
     * @param hostAddress is the ip address of the remote server
     * @param portNumber is the port number of the remote server
     */
    public CLI(String hostAddress, int portNumber){
        this.hostAddress = hostAddress;
        this.portNumber = portNumber;
        setMessageSender();
    }

    /**
     * This method set the MessageSender accordingly to the chosen mode, local or online
     */
    private void setMessageSender(){
        String input = readInput("Press L for local game or O for online game");

        while(!(input.equalsIgnoreCase("L") || input.equalsIgnoreCase("O"))) {
            System.out.println("Input not valid");
            input = readInput("Press L for local game or O for online game");
        }
        if (input.equalsIgnoreCase("L")) {
            System.out.println("LocalSender created");
            messageSender = new LocalSender(this);
        } else if (input.equalsIgnoreCase("O")) {
            System.out.println("ClientSocket created");
            //nel caso volessimo leggere l'input dell'hostname e della porta direttamente nella cli (ovviamente va fatto duale nella gui) e va sistemato clientMain
            //input = readInput("Press game");
            //input = readInput("Press L");
            messageSender = new ClientSocket(hostAddress, portNumber, this);
        }
    }


    /**
     * This method sets a thread of StdInReader and returns the line read from the StdInReader thread
     */
    public String readInput(String stringPrint) {
        System.out.println(stringPrint);
        FutureTask<String> futureTask = new FutureTask<>(new StdInReader());
        Thread inputThread = new Thread(futureTask);
        inputThread.start();

        String stdInLine = null;

        try {
            stdInLine = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return stdInLine;
    }

    /*public void createMatchReplyMessage(){
        int numberPlayer = 0;
        try {
            numberPlayer = Integer.parseInt(readInput("Inserisci numero di giocatori:"));
        }catch(NumberFormatException numberFormatException) {
            System.out.println("Invalid numberFormatException");
        }
        CreateMatchReplyMessage createMatchReplyMessage = new CreateMatchReplyMessage(readInput("Inserisci nickname:"), numberPlayer);
        System.out.println("Message created");
    }*/

    @Override
    public void showError(String errorString) {

    }

    @Override
    public void showInitialLeaderCardDiscard(String nickname, int indexLeaderCard1, int indexLeaderCard2) {

    }

    @Override
    public void showUpdatedTemporaryMapResource(String nickname, Map<Resource, Integer> temporaryMapResource) {

    }

    @Override
    public void showUpdatedWarehouse(String nickname, List<Map<Resource, Integer>> depots) {

    }

    @Override
    public void showUpdatedStrongbox(String nickname, Map<Resource, Integer> strongbox) {

    }

    @Override
    public void showUpdatedDevCardSpace(String nickname, ArrayList<ArrayList<Integer>> cardsState) {

    }


    /**
     * Sends message to client
     * @param message Message notified by {@link Observable}
     */
    @Override
    public void update(Message message) { //todo sistemare parametro Message in MessageToClient

    }
    
}
