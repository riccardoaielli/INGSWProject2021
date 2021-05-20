package it.polimi.ingsw.client;

import it.polimi.ingsw.client.LocalModel.LocalModel;
import it.polimi.ingsw.client.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.common.messages.messagesToServer.ChooseInitialResourcesMessage;
import it.polimi.ingsw.common.messages.messagesToServer.CreateMatchReplyMessage;
import it.polimi.ingsw.common.messages.messagesToServer.DiscardInitialLeaderMessage;
import it.polimi.ingsw.common.messages.messagesToServer.NicknameReplyMessage;
import it.polimi.ingsw.server.model.Observable;
import it.polimi.ingsw.server.model.RankPosition;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * This class is the ClientView
 */
public class CLI implements ClientView {

    private static String stdInLine;
    private MessageSender messageSender;
    private String hostAddress;
    private int portNumber;
    private LocalModel localModel;
    private LocalPhase phase;


    /**
     * This constructor creates the CLI and calls setMessageSender
     * @param hostAddress is the ip address of the remote server
     * @param portNumber is the port number of the remote server
     */
    public CLI(String hostAddress, int portNumber){
        this.hostAddress = hostAddress;
        this.portNumber = portNumber;
        localModel = new LocalModel();
        phase = LocalPhase.DEFAULT;
    }

    public void start(){
        System.out.println("\n" +
                " _______  _______  _______ _________ _______  _______  _______      _______  _______               \n" +
                "(       )(  ___  )(  ____ \\\\__   __/(  ____ \\(  ____ )(  ____ \\    (  ___  )(  ____ \\              \n" +
                "| () () || (   ) || (    \\/   ) (   | (    \\/| (    )|| (    \\/    | (   ) || (    \\/              \n" +
                "| || || || (___) || (_____    | |   | (__    | (____)|| (_____     | |   | || (__                  \n" +
                "| |(_)| ||  ___  |(_____  )   | |   |  __)   |     __)(_____  )    | |   | ||  __)                 \n" +
                "| |   | || (   ) |      ) |   | |   | (      | (\\ (         ) |    | |   | || (                    \n" +
                "| )   ( || )   ( |/\\____) |   | |   | (____/\\| ) \\ \\__/\\____) |    | (___) || )                    \n" +
                "|/     \\||/     \\|\\_______)   )_(   (_______/|/   \\__/\\_______)    (_______)|/                     \n" +
                "                                                                                                   \n" +
                " _______  _______ _________ _        _______  _______  _______  _______  _        _______  _______ \n" +
                "(  ____ )(  ____ \\\\__   __/( (    /|(  ____ \\(  ____ \\(  ____ \\(  ___  )( (    /|(  ____ \\(  ____ \\\n" +
                "| (    )|| (    \\/   ) (   |  \\  ( || (    \\/| (    \\/| (    \\/| (   ) ||  \\  ( || (    \\/| (    \\/\n" +
                "| (____)|| (__       | |   |   \\ | || (__    | (_____ | (_____ | (___) ||   \\ | || |      | (__    \n" +
                "|     __)|  __)      | |   | (\\ \\) ||  __)   (_____  )(_____  )|  ___  || (\\ \\) || |      |  __)   \n" +
                "| (\\ (   | (         | |   | | \\   || (            ) |      ) || (   ) || | \\   || |      | (      \n" +
                "| ) \\ \\__| (____/\\___) (___| )  \\  || (____/\\/\\____) |/\\____) || )   ( || )  \\  || (____/\\| (____/\\\n" +
                "|/   \\__/(_______/\\_______/|/    )_)(_______/\\_______)\\_______)|/     \\||/    )_)(_______/(_______/\n" +
                "                                                                                                   \n");
        setMessageSender();
    }

    @Override
    public LocalModel getLocalModel() {
        return localModel;
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
            //nel caso volessimo leggere l'input dell'hostname e della porta direttamente nella cli (ovviamente va fatto duale nella gui) e va sistemato clientMain
            //input = readInput("Press game");
            //input = readInput("Press L");
            messageSender = new ClientSocket(hostAddress, portNumber, this);
            System.out.println("ClientSocket created");
        }
    }

/*
    /**
     * Receive a message from the SocketInReader and update the clientView.
     */
    /*public void messageReadStdIn(String line) {
        this.SocketInReaderLine = line;
        message = messageToClientDeserializer.deserializeMessage(line);
        clientView.update(message);
    }
*/
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

    /**
     * This method tells the client if the player is the first one to connect
     */
    @Override
    public void askCreateMatch(){
        String numPlayerInput = readInput("How many players?");
        String nicknameInput = readInput("Insert nickname");
        messageSender.sendMessage(new CreateMatchReplyMessage(nicknameInput, Integer.parseInt(numPlayerInput)));
        localModel.setLocalPlayer(nicknameInput);
    }

    @Override
    public void askNickname(){
        String nicknameInput = readInput("Insert nickname");
        messageSender.sendMessage(new NicknameReplyMessage(nicknameInput));
        localModel.setLocalPlayer(nicknameInput);
    }

    @Override
    public void askResourceChoice() {
        int totalResources = 0;
        int numOfResourceToChoose = localModel.getNumOfResourceToChoose();
        System.out.println("Choose " + numOfResourceToChoose + " resource to add to your depot");
        Map<Resource,Integer> resources = new HashMap<>();
        String resourceType = "";
        String numOfResourceType = "";

        do {
            resourceType = readInput("Choose a resource type(COIN,SHIELD,SERVANT,STONE):");
            numOfResourceType = readInput("Choose how many resources you want of this type:");
            totalResources += Integer.parseInt(numOfResourceType);
            resources.put(Resource.valueOf(resourceType),Integer.parseInt(numOfResourceType));
        }while (totalResources < numOfResourceToChoose);
        messageSender.sendMessage(new ChooseInitialResourcesMessage(localModel.getLocalPlayer(),resources));
    }


    @Override
    public void showUpdateInitialLeaderCard(ArrayList<Integer> initialLeaderCardsID) {
        phase = LocalPhase.LEADER_CHOICE;
        localModel.setInitialLeaderCards(initialLeaderCardsID);
        askForLeaderCards();
    }

    @Override
    public void askForLeaderCards() {
        localModel.printLeaderCards();
        String firstCard = readInput("Choose a card to discard");
        String secondCard = readInput("Choose another card to discard");
        messageSender.sendMessage(new DiscardInitialLeaderMessage(localModel.getLocalPlayer(),Integer.parseInt(firstCard),Integer.parseInt(secondCard)));
    }

    @Override
    public void setPhase(LocalPhase phase) {
        this.phase = phase;
    }

    @Override
    public LocalPhase getPhase() {
        return phase;
    }


    @Override
    public void showError(String errorString) {
        System.out.println(errorString);
    }

    @Override
    public void showInitialLeaderCardDiscard(String nickname, int indexLeaderCard1, int indexLeaderCard2) {
        localModel.discardInitialLeaders(nickname,indexLeaderCard1,indexLeaderCard2);
        if(localModel.getLocalPlayer().equals(nickname))
            System.out.println("Wait for other players to choose their cards");
    }

    @Override
    public void showUpdatePlayersOrder(List<String> playersOrder) {
        int playerNumber = playersOrder.indexOf(localModel.getLocalPlayer())+1;
        System.out.println("You are player n°" + playerNumber);

    }

    @Override
    public void showUpdatedTemporaryMapResource(String nickname, Map<Resource, Integer> temporaryMapResource) {
        System.out.println(temporaryMapResource);
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

    @Override
    public void showUpdateTemporaryMarbles(String nickname, Map<Marble, Integer> temporaryMarbles) {

    }

    @Override
    public void showUpdateRedcrossPosition(String nickname, int redcrossPosition) {

    }

    @Override
    public void showUpdatePopeFavourTiles(String nickname, ArrayList<Integer> popeFavourTiles) {

    }

    @Override
    public void showUpdatePlayerTurn(String nickname) {
        if (nickname.equals(localModel.getLocalPlayer())){
            System.out.println("It's your turn");
            phase.handlePhase(this);
        }
        else {
            System.out.println("It's" + nickname + "'s turn");
        }





    }

    @Override
    public void showUpdateMarket(Marble[][] marketMatrix, Marble marbleOut) {
        localModel.setMarket(marketMatrix,marbleOut);
        if(phase == LocalPhase.LEADER_CHOICE)
            localModel.printMarket();
    }

    @Override
    public void showUpdateAddSpecialDepotUpdate(String nickname, Resource depotResourceType) {

    }

    @Override
    public void showUpdateCardGridUpdate(int[][] cardGridMatrixUpdate) {
        localModel.setCardGrid(cardGridMatrixUpdate);
        if(phase == LocalPhase.LEADER_CHOICE)
            localModel.printCardGrid();
    }

    @Override
    public void showUpdateDiscardedLeaderUpdate(String nickname, int leaderPosition) {

    }

    @Override
    public void showUpdateLeaderCardActivatedUpdate(String nickname, int numLeadercard, int leaderCardID) {

    }

    @Override
    public void showUpdateRank(String nickname, ArrayList<RankPosition> rank) {

    }

    @Override
    public void showUpdateFirstConnection(boolean firstPlayer) {

    }


    /**
     * Sends message to client
     * @param message Message notified by {@link Observable}
     */
    @Override
    public void update(MessageToClient message) {
        message.handleMessage(this);
    }
    
}
