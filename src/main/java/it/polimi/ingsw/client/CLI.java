package it.polimi.ingsw.client;

import it.polimi.ingsw.client.LocalModel.LocalModel;
import it.polimi.ingsw.client.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.common.messages.messagesToServer.*;
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
    private boolean mainTurnActionDone;


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
        mainTurnActionDone = false;
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

    public void clearConsoleAndReprint() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        localModel.printView();
        //Add reprint view
    }

    private Resource readResource(){
        String resourceType;
        resourceType = readInput("Choose a resource type(COIN,SHIELD,SERVANT,STONE):").toUpperCase();
        while (!(resourceType.equals("COIN") || resourceType.equals("SHIELD") || resourceType.equals("SERVANT") || resourceType.equals("STONE"))){
            resourceType = readInput("Incorrect value, please insert a value among COIN,SHIELD,SERVANT or STONE ").toUpperCase();
        }
        return Resource.valueOf(resourceType);
    }

    private int readResourceQuantity(){
        String numOfResourceType = readInput("Choose how many resources you want of this type (insert a number >= 1");
        int numOfResource =  0;
        try {
            numOfResource =  Integer.parseInt(numOfResourceType);
        } catch (NumberFormatException e) {
            System.out.println("Not a number");
        }
        while(numOfResource<=0){
            numOfResourceType = readInput("Please insert a number >= 1");
            try {
                numOfResource =  Integer.parseInt(numOfResourceType);
            } catch (NumberFormatException e) {
                System.out.println("Not a number");
            }
        }
        return numOfResource;
    }

    private List<Map<Resource, Integer>> readCostStrongboxWarehouse(){
        String paymentMethod = readInput("Choose how you want to pay: type S (Strongbox) or W (Warehouse)").toUpperCase();
        while(!(paymentMethod.equals("S")||paymentMethod.equals("W"))){
            paymentMethod = readInput("Invalid choice, type S or W").toUpperCase();
        }
        Map<Resource, Integer>costStrongbox = new HashMap<>();
        Map<Resource, Integer>costWarehouse = new HashMap<>();
        do{
            Resource resource = readResource();
            int resourceQuantity = readResourceQuantity();
            if (paymentMethod.equals("S")){
                costStrongbox.merge(resource, resourceQuantity, Integer::sum);
            }
            else{
                costWarehouse.merge(resource, resourceQuantity, Integer::sum);
            }

            paymentMethod = readInput("Choose how you want to pay: type S (Strongbox), W (Warehouse) or Q to quit if you have finished choosing how to pay").toUpperCase();
            while(!(paymentMethod.equals("S")||paymentMethod.equals("W")||paymentMethod.equals("Q"))){
                paymentMethod = readInput("Invalid choice, type S, W or Q").toUpperCase();
            }
        }while (!paymentMethod.equals("Q"));
        List<Map<Resource, Integer>> costStrongboxWarehouse = new ArrayList<>();
        costStrongboxWarehouse.add(costStrongbox);
        costStrongboxWarehouse.add(costWarehouse);
        return costStrongboxWarehouse;
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
     * Method to read an int from stdin
     * @param stringPrint String to print before the users insert a value from keyboard
     * @return the parsed int
     */
    public int readInt(String stringPrint) {
        String intString = readInput(stringPrint);
        int numOfResource;
        while (true) {
            try {
                numOfResource = Integer.parseInt(intString);
                return numOfResource;
            } catch (NumberFormatException e) {
                System.out.println("Not a number");
                System.out.println(stringPrint);
            }
        }
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

        do {
            Resource resource = readResource();
            int numOfResourceType = readResourceQuantity();
            totalResources += numOfResourceType;
            resources.put(resource,numOfResourceType);
        }while (totalResources < numOfResourceToChoose);
        messageSender.sendMessage(new ChooseInitialResourcesMessage(localModel.getLocalPlayer().getNickname(),resources));
    }

    @Override
    public void askAddToWareHouse() {
        System.out.println("Add your obtained resource to your warehouse");
        Resource resource = readResource();
        int numOfResourceType = readResourceQuantity();

        Map<Resource, Integer> singleResourceMap = new HashMap<>();
        singleResourceMap.put(resource, numOfResourceType);

        int depotLevel=0;
        try {
            depotLevel = Integer.parseInt(readInput("Choose the depot level, insert a number between 1 and 5\n(1,2,3 Standard Depot, 4,5 special depot if active): "));
        } catch (NumberFormatException e) {
            System.out.println("Not a number");
        }
        while(!(depotLevel>=1 && depotLevel <= 5)){
            try {
                depotLevel = Integer.parseInt(readInput("Please insert a number between 1 and 5\n(1,2,3 Standard Depot or 4,5 Special depot if active): "));
            } catch (NumberFormatException e) {
                System.out.println("Not a number");
            }
        }

        messageSender.sendMessage(new AddToWarehouseMessage(localModel.getLocalPlayer().getNickname(), depotLevel, singleResourceMap));
    }

    @Override
    public void askTurnAction() {

        localModel.printView();

        int numOfActions = 3;
        List<String> actions = new ArrayList<>();
        List<LocalPhase> actionPhases = new ArrayList<>();
        if (!mainTurnActionDone) {
            actions.add("Take Resources from market");
            actionPhases.add(LocalPhase.TAKE_FROM_MARKET);
            actions.add("Buy one Development Card");
            actionPhases.add(LocalPhase.BUY_DEV_CARD);
            actions.add("Activate the Production");
            actionPhases.add(LocalPhase.ACTIVATE_PRODUCTION);
            numOfActions = 6;
        }
        else
            System.out.println("0. End turn");
        actions.add("Activate a leader card");
        actionPhases.add(LocalPhase.ACTIVATE_LEADER);
        actions.add("Discard a leader card");
        actionPhases.add(LocalPhase.DISCARD_LEADER);
        actions.add("Rearrange warehouse resources");
        actionPhases.add(LocalPhase.REARRANGE_WAREHOUSE);

        System.out.println("Choose an action:");
        for (int action = 0; action < numOfActions; action++) {
            System.out.println((action + 1) + ". " + actions.get(action));
        }

        int action = readInt("Insert a number between 1 and " + numOfActions + " :");
        while (!(action >= 0 && action <= numOfActions)) {
            action = readInt("Please insert a number between 1 and " + numOfActions + " :");
        }

        if (action == 0) {
            messageSender.sendMessage(new EndTurnMessage(localModel.getLocalPlayer().getNickname()));
        } else{
            phase = actionPhases.get(action - 1);
            phase.handlePhase(this);
            }
        /*
        if (phase == LocalPhase.MAIN_TURN_ACTION_AVAILABLE){
            System.out.println("Choose an action:\n" +
                    "1. Take Resources from market\n" +
                    "2. Buy one Development Card\n" +
                    "3. Activate the Production\n" +
                    "4. Activate a leader card\n" +
                    "5. Discard a leader card\n" +
                    "6. Rearrange warehouse resources");
            String action = readInput("Insert a number between 1 and 6:");
            int actionInt =  Integer.parseInt(action);
            while(!(actionInt>=1 && actionInt<=6)){
                action = readInput("Please insert a number between 1 and 6");
                try {
                    actionInt =  Integer.parseInt(action);
                } catch (NumberFormatException e) {
                    System.out.println("Not a number");
                }
            }
            switch (actionInt){
                case 1:
                    phase = LocalPhase.TAKE_FROM_MARKET;
                    break;
                case 2:
                    phase = LocalPhase.BUY_DEV_CARD;
                    break;
                case 3:
                    phase = LocalPhase.ACTIVATE_PRODUCTION;
                    break;
                case 4:
                    phase = LocalPhase.ACTIVATE_LEADER;
                    break;
                case 5:
                    phase = LocalPhase.DISCARD_LEADER;
                    break;
                case 6:
                    phase = LocalPhase.REARRANGE_WAREHOUSE;
            }
            phase.handlePhase(this);
        }*/
    }


    @Override
    public void askTakeFromMarketAction() {
        String rowOrColumn;
        do{
            rowOrColumn = readInput("Choose if you want to buy from a 'row' or a 'column':");
        }while (!(rowOrColumn.equals("row") || rowOrColumn.equals("column")));
        String numOfRowOrColumn;
        int numOfRowOrColumnInt = 0;
        int rowOrColumnInt;
        int max;
        if (rowOrColumn.equals("row")) {
            max = 3;
            rowOrColumnInt = 0;
        }
        else {
            max = 4;
            rowOrColumnInt = 1;
        }
        do {
            numOfRowOrColumn = readInput("Insert the number of " + rowOrColumn + " you want to buy from (a number from 1 to " + max + "): " );
            try {
                numOfRowOrColumnInt =  Integer.parseInt(numOfRowOrColumn);
            } catch (NumberFormatException e) {
                System.out.println("Not a number");
            }
        }while ( !(numOfRowOrColumnInt > 0 && numOfRowOrColumnInt <= max) );

        messageSender.sendMessage(new TakeFromMarketMessage(localModel.getLocalPlayer().getNickname(),rowOrColumnInt,(numOfRowOrColumnInt -1)));
    }

    @Override
    public void askForLeaderPower() {
        String firstAnswer;
        do{
            firstAnswer = readInput("Do you want to use a leader power to transform white marbles?[y/n]");
        }while (!(firstAnswer.equals("y")||firstAnswer.equals("n")));
        if (firstAnswer.equals("y")){
            String leaderCard;
            int leaderCardInt = 0;
            String numOfTransformations;
            int numOfTransformationsInt = 0;
            do {
                leaderCard = readInput("Choose the number of leader card to use:");
                try {
                    leaderCardInt =  Integer.parseInt(leaderCard);
                } catch (NumberFormatException e) {
                    System.out.println("Not a number");
                }
            }while (leaderCardInt < 0);
            do {
                numOfTransformations = readInput("Choose the number of white marbles to transform:");
                try {
                    numOfTransformationsInt =  Integer.parseInt(numOfTransformations);
                } catch (NumberFormatException e) {
                    System.out.println("Not a number");
                }
            }while (numOfTransformationsInt < 0);
            messageSender.sendMessage(new TransformWhiteMarblesMessage(localModel.getLocalPlayer().getNickname(),leaderCardInt,numOfTransformationsInt));
        }
        else{
            messageSender.sendMessage(new TransformMarblesMessage(localModel.getLocalPlayer().getNickname()));
        }
    }

    @Override
    public void askBuyDevCard() {
        System.out.println("Insert the coordinates of the development card you want to buy");
        String rowString = readInput("Insert the row of the card (number between 1 and 3)");
        int row =  0;
        try {
            row =  Integer.parseInt(rowString);
        } catch (NumberFormatException e) {
            System.out.println("Not a number");
        }
        while(!(row>=1&&row <=3)){
            rowString = readInput("Please insert a number between 1 and 3)");
            try {
                row =  Integer.parseInt(rowString);
            } catch (NumberFormatException e) {
                System.out.println("Not a number");
            }
        }

        String columnString = readInput("Insert the row of the card (number between 1 and 4)");
        int column =  0;
        try {
            column =  Integer.parseInt(columnString);
        } catch (NumberFormatException e) {
            System.out.println("Not a number");
        }
        while(!(column>=1&&column <=4)){
            columnString = readInput("Please insert a number between 1 and 4");
            try {
                column =  Integer.parseInt(columnString);
            } catch (NumberFormatException e) {
                System.out.println("Not a number");
            }
        }
        List<Map<Resource, Integer>> costStrongboxWarehouse = readCostStrongboxWarehouse();
        int numLeaderCard = readInt("Choose 0 if you do not want to use a leader card, otherwise insert the position of an active leader card with a discount power if you have it:");
        while (!(numLeaderCard>= 0)){
            numLeaderCard = readInt("Invalid number, choose a number >= 0");
        }

        int cardPosition = readInt("Choose the slot where you want to place the card, insert 1, 2 or 3 indicating the slots from left to right:");
        while (!(cardPosition>= 1 && cardPosition<=3)){
           cardPosition = readInt("Invalid number, choose a number between 1 and 3");
        }
        messageSender.sendMessage(new BuyDevelopmentCardMessage(localModel.getLocalPlayer().getNickname(), row, column,costStrongboxWarehouse.get(0), costStrongboxWarehouse.get(1), numLeaderCard, cardPosition));
    }

    @Override
    public void askProduction() {
        int productionChoice = readInt("Choose which kind of production you want to activate:\n" +
                "1. Basic Production\n" +
                "2. Leader Production\n" +
                "3. Card Production\n");
        while (!(productionChoice>= 1 && productionChoice <=3)){
            productionChoice = readInt("Invalid number, choose a number between 1 and 3");
        }
        switch (productionChoice){
            case 1:
                askBasicProduction();
                break;
            case 2:

                break;
            case 3:

                break;
        }

    }

    private void askBasicProduction(){
        List<Map<Resource, Integer>> costStrongboxWarehouse = readCostStrongboxWarehouse();
        Resource resource = readResource();
        messageSender.sendMessage(new ActivateBasicProductionMessage(getLocalModel().getLocalPlayer().getNickname(), costStrongboxWarehouse.get(0), costStrongboxWarehouse.get(1), resource));
    }

    private void askLeaderProduction(){

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
        messageSender.sendMessage(new DiscardInitialLeaderMessage(localModel.getLocalPlayer().getNickname(),Integer.parseInt(firstCard),Integer.parseInt(secondCard)));
    }

    @Override
    public void askActivateLeader() {
        int numLeaderCard = readInt("Choose the number of the card to activate: ");
        while (numLeaderCard <= 0){
            numLeaderCard = readInt("Choose the number of the card to activate: ");
        }
        messageSender.sendMessage(new ActivateLeaderMessage(localModel.getLocalPlayer().getNickname(),numLeaderCard));
    }

    @Override
    public void askDiscardLeader(){
        int numLeaderCard = readInt("Choose the number of the card to discard: ");
        while (numLeaderCard <= 0){
            numLeaderCard = readInt("Choose the number of the card to discard: ");
        }
        messageSender.sendMessage(new DiscardLeaderMessage(localModel.getLocalPlayer().getNickname(),numLeaderCard));
    }

    @Override
    public void setPhase(LocalPhase phase) {
        this.phase = phase;
    }

    @Override
    public void setMainTurnActionDone(boolean mainTurnActionDone) {
        this.mainTurnActionDone = mainTurnActionDone;
    }

    @Override
    public LocalPhase getPhase() {
        return phase;
    }

    @Override
    public void showCurrentPlayer(String nickname) {
        System.out.println("wait, " + nickname +" is playing");
    }



    @Override
    public void showError(String errorString) {
        System.out.println(errorString);
    }

    @Override
    public void showInitialLeaderCardDiscard(String nickname, int indexLeaderCard1, int indexLeaderCard2) {
        localModel.discardInitialLeaders(nickname,indexLeaderCard1,indexLeaderCard2);
        if(localModel.getLocalPlayer().getNickname().equals(nickname))
            System.out.println("Wait for other players to choose their cards...");
    }

    @Override
    public void showUpdatePlayersOrder(List<String> playersOrder) {
        localModel.setPlayersOrder(playersOrder);
        int playerNumber = playersOrder.indexOf(localModel.getLocalPlayer().getNickname())+1;
        System.out.println("You are player n°" + playerNumber);

    }

    @Override
    public void showUpdatedTemporaryMapResource(String nickname, Map<Resource, Integer> temporaryMapResource) {
        System.out.println(nickname + temporaryMapResource);
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
        localModel.getPlayer(nickname).setTemporaryMarbles(temporaryMarbles);
        System.out.println(nickname + " obtained these marbles from the market:");
        localModel.getPlayer(nickname).printTermporaryMarbles();
    }

    @Override
    public void showUpdateRedcrossPosition(String nickname, int redcrossPosition) {
        localModel.getPlayer(nickname).setRedCrossPosition(redcrossPosition);
    }

    @Override
    public void showUpdatePopeFavourTiles(String nickname, ArrayList<Integer> popeFavourTiles) {
        localModel.getPlayer(nickname).setPopeFavourTiles(popeFavourTiles);
    }

    @Override
    public void showUpdatePlayerTurn(String nickname) {
        if (nickname.equals(localModel.getLocalPlayer().getNickname())){
            System.out.println("It's your turn");
        }
        else {
            System.out.println("It's " + nickname + "'s turn");
        }


    }

    @Override
    public void showUpdateMarket(Marble[][] marketMatrix, Marble marbleOut) {
        localModel.setMarket(marketMatrix,marbleOut);
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
        //todo: rimuovere la carta leader dal localModel
        System.out.println("The card has been discarded");
    }

    @Override
    public void showUpdateLeaderCardActivatedUpdate(String nickname, int numLeadercard, int leaderCardID) {
        //todo: attivare la carta nel localModel
        System.out.println("The card has been activated");
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
