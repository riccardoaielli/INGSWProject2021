package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.client.CLI.LocalModel.LocalModel;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.client.CLI.LocalModel.CliColor;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.common.messages.messagesToServer.*;
import it.polimi.ingsw.server.model.ObservableGameEnder;
import it.polimi.ingsw.server.model.RankPosition;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is the ClientView
 */
public class CLI implements ClientView {

    private MessageSender messageSender;
    private String hostAddress;
    private int portNumber;
    private LocalModel localModel;
    private LocalPhase phase;
    private boolean mainTurnActionDone;
    private boolean firstTurn;
    private boolean demo;

    private StdInReader readLineThread;


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
        firstTurn = true;
        demo = false;

        readLineThread = new StdInReader();
        readLineThread.start();
    }

    /**
     * Prints the title and sets the message sender
     */
    public void start(){
        localModel.printTitle();
        setMessageSender();
    }

    /**
     * Clears the console and prints the view with all the players
     */
    public void clearConsoleAndReprint() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        localModel.printView();
        //Add reprint view
    }

    /**
     * Method to read a resource
     * @return the resource typed by the user
     */
    private Resource readResource(){
        String resourceType;
        resourceType = readInput("Choose a resource type(COIN,SHIELD,SERVANT,STONE):").toUpperCase();
        while (!(resourceType.equals("COIN") || resourceType.equals("SHIELD") || resourceType.equals("SERVANT") || resourceType.equals("STONE"))){
            resourceType = readInput("Incorrect value, please insert a value among COIN,SHIELD,SERVANT or STONE ").toUpperCase();
        }
        return Resource.valueOf(resourceType);
    }

    /**
     * Method to read a quantity
     * @return an int typed by the user
     */
    private int readResourceQuantity(){
        String numOfResourceType = readInput("Choose how many resources of this type (insert a number >= 1)");
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

            paymentMethod = readInput("Choose how you want to pay: type S (Strongbox), W (Warehouse) or D (Done) if you have finished choosing the resources to pay").toUpperCase();
            while(!(paymentMethod.equals("S")||paymentMethod.equals("W")||paymentMethod.equals("D"))){
                paymentMethod = readInput("Invalid choice, type S, W or D").toUpperCase();
            }
        }while (!paymentMethod.equals("D"));
        List<Map<Resource, Integer>> costStrongboxWarehouse = new ArrayList<>();
        costStrongboxWarehouse.add(costStrongbox);
        costStrongboxWarehouse.add(costWarehouse);
        return costStrongboxWarehouse;
    }

    /**
     * Getter for the local model of the view
     * @return a local model
     */
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
            messageSender = new LocalSender(this);
        } else if (input.equalsIgnoreCase("O")) {
            //nel caso volessimo leggere l'input dell'hostname e della porta direttamente nella cli (ovviamente va fatto duale nella gui) e va sistemato clientMain
            //input = readInput("Press game");
            //input = readInput("Press L");
            messageSender = new ClientSocket(hostAddress, portNumber, this);
        }
    }

    /**
     * This method prints a string and reads from stdIn
     */
    public String readInput(String stringPrint) {
        System.out.println(stringPrint);
        return readLineThread.readUserInput();
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
                intString = readInput(stringPrint);
            }
        }
    }

    /**
     * This method tells the client if the player is the first one to connect
     */
    @Override
    public void askCreateMatch(){
        int numPlayerInput = readInt("How many players?");
        String nicknameInput = readInput("Insert nickname");
        localModel.setLocalPlayer(nicknameInput);
        messageSender.sendMessage(new CreateMatchReplyMessage(nicknameInput, numPlayerInput));
        if(numPlayerInput != 1)
            System.out.println("Wait for other players to join the game...");
    }

    /**
     * Method to ask a player the nickname to join the match
     */
    @Override
    public void askNickname(){
        String nicknameInput = readInput("Insert nickname");
        localModel.setLocalPlayer(nicknameInput);
        messageSender.sendMessage(new NicknameReplyMessage(nicknameInput));
        System.out.println("Wait for other players to join the game...");
    }

    /**
     * Method to ask the player to choose it's starting resources
     */
    @Override
    public void askResourceChoice() {
        int totalResources = 0;
        int numOfResourceToChoose = localModel.getNumOfResourceToChoose();
        if(numOfResourceToChoose>1){
            System.out.println("Choose " + numOfResourceToChoose + " resources to add to your depot");
        }
        else{
            System.out.println("Choose " + numOfResourceToChoose + " resource to add to your depot");
        }
        Map<Resource,Integer> resources = new HashMap<>();

        do {
            Resource resource = readResource();
            int numOfResourceType = readResourceQuantity();
            totalResources += numOfResourceType;
            resources.put(resource,numOfResourceType);
        }while (totalResources < numOfResourceToChoose);
        messageSender.sendMessage(new ChooseInitialResourcesMessage(localModel.getLocalPlayer().getNickname(),resources));
    }

    /**
     * Method to ask the player the resources to add to its warehouse
     */
    @Override
    public void askAddToWareHouse() {
        if(firstTurn)
            addResourceToDepot();
        else if(localModel.getLocalPlayer().getTemporaryMapResource().isEmpty()){
            phase = LocalPhase.MENU;
            phase.handlePhase(this);
        }
        else {
            int addWarehouseChoice = readInt("Choose one of the following actions:\n" +
                    "1. Add your obtained resource to your warehouse\n" +
                    "2. Rearrange your warehouse\n" +
                    "3. Discard the obtained resources");
            while (!(addWarehouseChoice >= 1 && addWarehouseChoice <= 3)) {
                addWarehouseChoice = readInt("Invalid number, choose a number between 1 and 3");
            }
            switch (addWarehouseChoice) {
                case 1:
                    addResourceToDepot();
                    break;
                case 2:
                    phase = LocalPhase.REARRANGE_WAREHOUSE;
                    phase.handlePhase(this);
                    break;
                case 3:
                    messageSender.sendMessage(new DiscardResourcesFromMarketMessage(localModel.getLocalPlayer().getNickname()));
                    localModel.getLocalPlayer().setTemporaryMapResource(new HashMap<>());
                    break;
            }
        }
    }

    private void addResourceToDepot(){
        Resource resource = readResource();
        int numOfResourceType = readResourceQuantity();

        Map<Resource, Integer> singleResourceMap = new HashMap<>();
        singleResourceMap.put(resource, numOfResourceType);

        int depotLevel = readInt("Choose the depot level, insert a number between 1 and 5\n(1,2,3 Standard Depot, 4,5 special depot if active): ");
        while(!(depotLevel>=1 && depotLevel <= 5)){
            depotLevel = readInt("Please insert a number between 1 and 5\n(1,2,3 Standard Depot or 4,5 Special depot if active): ");
        }

        messageSender.sendMessage(new AddToWarehouseMessage(localModel.getLocalPlayer().getNickname(), depotLevel, singleResourceMap));
    }

    /**
     * Method to ask the player an action to do
     */
    @Override
    public void askTurnAction() {
        if(firstTurn)
            firstTurn = false;
        clearConsoleAndReprint();

        int firstAction = 0;
        int numOfActions = 3;
        List<String> actions = new ArrayList<>();
        List<LocalPhase> actionPhases = new ArrayList<>();
        System.out.println("Choose an action:");
        if (!mainTurnActionDone) {
            actions.add("Take Resources from market");
            actionPhases.add(LocalPhase.TAKE_FROM_MARKET);
            actions.add("Buy one Development Card");
            actionPhases.add(LocalPhase.BUY_DEV_CARD);
            actions.add("Activate the Production");
            actionPhases.add(LocalPhase.ACTIVATE_PRODUCTION);
            firstAction = 1;
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


        for (int action = 0; action < numOfActions; action++) {
            System.out.println((action + 1) + ". " + actions.get(action));
        }
        System.out.println((numOfActions+1) + ". Quit Game");

        int action = readInt("Insert a number between "+ firstAction + " and " + (numOfActions + 1) + " :");
        while (!(action >= 0 && action <= numOfActions + 1)) {
            action = readInt("Insert a number between "+ firstAction + " and " + (numOfActions + 1) + " :");
        }

        if (action == 0) {
            messageSender.sendMessage(new EndTurnMessage(localModel.getLocalPlayer().getNickname()));
        } else if(action == numOfActions+1) {
                    closeGame("Quitting...");
            }
            else{
                phase = actionPhases.get(action - 1);
                phase.handlePhase(this);
            }


    }

    /**
     * Method to ask the player the coordinates to buy from the market
     */
    @Override
    public void askTakeFromMarketAction() {
        String rowOrColumn;
        do{
            rowOrColumn = readInput("Choose if you want to buy from a 'row' or a 'column':").toLowerCase();
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

    /**
     * Method to ask the player to rearrange it's depots
     */
    @Override
    public void askRearrange() {
        int rearrangeChoice = readInt("0. Go back to menu\n" +
                "1. Move to/from special depot\n" +
                "2. Swap resources in standard depot shelves\n");
        while (!(rearrangeChoice>= 0 && rearrangeChoice <=2)){
            rearrangeChoice = readInt("Invalid number, choose a number between 0 and 2");
        }
        //Back to main menu
        if (rearrangeChoice == 0){
            phase = LocalPhase.MENU;
            phase.handlePhase(this);
        }
        else if (rearrangeChoice == 1) askMoveSD();
        else askSwap();
    }

    private void askMoveSD(){
        int sourceDepotNumber = readInt("Insert the number of the depot from which you want to move resources, insert a number between 1 and 5\n" +
                "(1,2,3 Standard Depot, 4,5 special depot): ");
        while(!(sourceDepotNumber>=1 && sourceDepotNumber <= 5)){
            sourceDepotNumber = readInt("Please insert a number between 1 and 5\n(1,2,3 Standard Depot or 4,5 Special depot if active): ");
        }

        int destinationDepotNumber = readInt("Insert the number of the depot to which you want to move resources, insert a number between 1 and 5\n" +
                "(1,2,3 Standard Depot, 4,5 special depot): ");
        while(!(destinationDepotNumber>=1 && destinationDepotNumber <= 5)){
            destinationDepotNumber = readInt("Please insert a number between 1 and 5\n(1,2,3 Standard Depot or 4,5 Special depot if active): ");
        }

        int quantity = readInt("Insert how many resources you want to move");
        while(!(quantity>=1)){
            quantity = readInt("Please insert a positive number");
        }

        messageSender.sendMessage(new MoveMessage(localModel.getLocalPlayer().getNickname(), sourceDepotNumber, destinationDepotNumber, quantity));
    }

    private void askSwap(){
        int[] depot = new int[2];
        for(int i = 0; i < 2; i++){
            int depotNumber = readInt("Insert the number of one of the two standard depots you want to swap, insert a number between 1 and 3\n" +
                    "(1,2,3 Standard Depot, 4,5 special depot): ");
            while(!(depotNumber>=1 && depotNumber <= 3)){
                depotNumber = readInt("Please insert a number between 1 and 3\n");
            }
            depot[i] = depotNumber;
        }

        messageSender.sendMessage(new SwapMessage(localModel.getLocalPlayer().getNickname(), depot[0], depot[1]));
    }

    /**
     * Method to ask the player a leader power to use
     */
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
            }while (numOfTransformationsInt <= 0);
            messageSender.sendMessage(new TransformWhiteMarblesMessage(localModel.getLocalPlayer().getNickname(),leaderCardInt,numOfTransformationsInt));
        }
        else{
            messageSender.sendMessage(new TransformMarblesMessage(localModel.getLocalPlayer().getNickname()));
        }
    }

    /**
     * Method to ask the player a card to buy
     */
    @Override
    public void askBuyDevCard() {
        System.out.println("Insert the coordinates of the development card you want to buy[type 0 to go back to menu]");
        int row = readInt("Choose the row of the card (1,2 or 3):");

        while(!(row>=0&&row <=3)){
            row = readInt("Please insert a number between 1 and 3)");
        }
        if(row == 0) {
            setPhase(LocalPhase.MENU);
            getPhase().handlePhase(this);
            return;
        }

        int column = readInt("Choose the column of the card (1,2,3 or 4):");

        while(!(column>=0&&column <=4)){
            column = readInt("Please insert a number between 1 and 4");
        }
        if(column == 0) {
            setPhase(LocalPhase.MENU);
            getPhase().handlePhase(this);
            return;
        }

        List<Map<Resource, Integer>> costStrongboxWarehouse = readCostStrongboxWarehouse();
        int numLeaderCard = readInt("Choose the number of a leader card to use it's discount power (type 0 if you don't want to use a leader card): ");
        while (!(numLeaderCard>= 0)){
            numLeaderCard = readInt("Invalid number, choose a number >= 0");
        }

        int cardPosition = readInt("Choose the slot where you want to place the card (1,2 or 3):");
        while (!(cardPosition>= 1 && cardPosition<=3)){
           cardPosition = readInt("Invalid number, choose a number between 1 and 3");
        }
        messageSender.sendMessage(new BuyDevelopmentCardMessage(localModel.getLocalPlayer().getNickname(), row, column,costStrongboxWarehouse.get(0), costStrongboxWarehouse.get(1), numLeaderCard, cardPosition));
    }

    /**
     * Method to ask the player a production to activate
     */
    @Override
    public void askProduction() {
        int productionChoice = readInt("Choose which kind of production you want to activate:" +
                "\n1. Basic Production" +
                "\n2. Leader Production" +
                "\n3. Card Production" +
                "\n4. End production");
        while (!(productionChoice>= 1 && productionChoice <=4)){
            productionChoice = readInt("Invalid number, choose a number between 1 and 4");
        }
        switch (productionChoice){
            case 1:
                askBasicProduction();
                break;
            case 2:
                askLeaderProduction();
                break;
            case 3:
                askCardProduction();
                break;
            case 4:
                messageSender.sendMessage(new EndProduction(getNickname()));
                break;
        }

    }

    /**
     * Method used to request information to the player to perform a basic production
     */
    private void askBasicProduction(){
        List<Map<Resource, Integer>> costStrongboxWarehouse = readCostStrongboxWarehouse();
        Resource resource = readResource();
        messageSender.sendMessage(new ActivateBasicProductionMessage(getLocalModel().getLocalPlayer().getNickname(), costStrongboxWarehouse.get(0), costStrongboxWarehouse.get(1), resource));
    }

    /**
     * Method used to request information to the player to perform a leader production
     */
    private void askLeaderProduction(){
        List<Map<Resource, Integer>> costStrongboxWarehouse = readCostStrongboxWarehouse();

        int numLeaderCard = readInt("Insert the position of an active leader card with a production power:");
        while (!(numLeaderCard>= 1)){
            numLeaderCard = readInt("Invalid number, choose a number >= 1");
        }
        System.out.println("Choose the resource you want to produce");
        Resource resource = readResource();

        messageSender.sendMessage(new ActivateLeaderProductionMessage(getLocalModel().getLocalPlayer().getNickname(), costStrongboxWarehouse.get(0), costStrongboxWarehouse.get(1), numLeaderCard, resource));
    }

    /**
     * Method used to request information to the player to perform a basic production
     */
    private void askCardProduction(){
        List<Map<Resource, Integer>> costStrongboxWarehouse = readCostStrongboxWarehouse();

        int cardPosition = readInt("Choose the slot of the card whose production you want to use:");
        while (!(cardPosition>= 1 && cardPosition<=3)){
            cardPosition = readInt("Invalid number, choose a number between 1 and 3");
        }

        messageSender.sendMessage(new ActivateCardProductionMessage(getLocalModel().getLocalPlayer().getNickname(), costStrongboxWarehouse.get(0), costStrongboxWarehouse.get(1), cardPosition));
    }

    /**
     * Method to update the initial leader cards received from the Model
     * @param initialLeaderCardsID a list of four leader cards
     */
    @Override
    public void showUpdateInitialLeaderCard(ArrayList<Integer> initialLeaderCardsID) {
        localModel.setInitialLeaderCards(initialLeaderCardsID);
        askForLeaderCards();
    }

    /**
     * Method to ask the player two leader cards to discard
     */
    @Override
    public void askForLeaderCards() {
        localModel.printInitialLeaderCards();
        int firstCard = readInt("Choose a card to discard: ");
        while (firstCard <= 0){
            firstCard = readInt("Choose a card to discard: ");
        }
        int secondCard = readInt("Choose another card to discard");
        while (secondCard <= 0){
            secondCard = readInt("Choose another card to discard: ");
        }
        messageSender.sendMessage(new DiscardInitialLeaderMessage(localModel.getLocalPlayer().getNickname(),firstCard,secondCard));
    }

    /**
     * Method to ask a player a leader card to activate
     */
    @Override
    public void askActivateLeader() {
        int numLeaderCard = readInt("Choose the number of the card to activate: ");
        while (numLeaderCard < 0 || numLeaderCard > 2){
            numLeaderCard = readInt("-Type 0 to go back to menu.\nChoose the number of the card to activate: ");
        }
        if(numLeaderCard == 0){
            phase = LocalPhase.MENU;
            phase.handlePhase(this);
        }
        else
            messageSender.sendMessage(new ActivateLeaderMessage(localModel.getLocalPlayer().getNickname(),numLeaderCard));
    }

    /**
     * Method to close the game
     * @param string the reason why the game is closed
     */
    @Override
    public void closeGame(String string) {
        System.out.println(string);
        System.exit(0);
    }

    /**
     * Method to ask a player a leader card to discard
     */
    @Override
    public void askDiscardLeader(){
        int numLeaderCard = readInt("Choose the number of the card to discard: ");
        while (numLeaderCard < 0 || numLeaderCard > 2){
            numLeaderCard = readInt("-Type 0 to go back to menu.\nChoose the number of the card to discard: ");
        }
        if(numLeaderCard == 0){
            phase = LocalPhase.MENU;
            phase.handlePhase(this);
        }
        messageSender.sendMessage(new DiscardLeaderMessage(localModel.getLocalPlayer().getNickname(),numLeaderCard));
    }

    /**
     * Method to update the turn phase of the view
     * @param phase the update of the phase
     */
    @Override
    public void setPhase(LocalPhase phase) {
        this.phase = phase;
    }

    /**
     * Method to set if the player did or not the main turn action
     * @param mainTurnActionDone true if the player already did the action
     */
    @Override
    public void setMainTurnActionDone(boolean mainTurnActionDone) {
        this.mainTurnActionDone = mainTurnActionDone;
    }

    /**
     * Getter for the phase of the view
     * @return the current phase of the view
     */
    @Override
    public LocalPhase getPhase() {
        return phase;
    }

    /**
     * Method to print an error received from the Model
     * @param errorString
     */
    @Override
    public void showError(String errorString) {
        System.out.println(errorString);
    }

    /**
     * Method to update the leader cards received from the Model
     * @param nickname the nickname of the player that received the update
     * @param indexLeaderCard1 the index of the first leader card
     * @param indexLeaderCard2 the index of the second leader card
     */
    @Override
    public void showInitialLeaderCardDiscard(String nickname, int indexLeaderCard1, int indexLeaderCard2) {
        localModel.discardInitialLeaders(nickname,indexLeaderCard1,indexLeaderCard2);
        if(localModel.getLocalPlayer().getNickname().equals(nickname))
            System.out.println("Wait for other players to choose their cards...");
    }

    /**
     * Method to update the players order received from the Model
     * @param playersOrder a list of players
     */
    @Override
    public void showUpdatePlayersOrder(List<String> playersOrder) {
        localModel.setPlayersOrder(playersOrder);
        int playerNumber = playersOrder.indexOf(localModel.getLocalPlayer().getNickname())+1;
        System.out.println("You are player n°" + playerNumber);

    }

    /**
     * Method to update the temporary resources received from the Model
     * @param nickname the nickname of the player that received the update
     * @param temporaryMapResource the updated temporary resources
     */
    @Override
    public void showUpdatedTemporaryMapResource(String nickname, Map<Resource, Integer> temporaryMapResource) {
        localModel.getPlayer(nickname).setTemporaryMapResource(temporaryMapResource);
        localModel.getPlayer(nickname).printTermporaryResource();
    }

    /**
     * Method to update the warehouse received from the Model
     * @param nickname the nickname of the player that received the update
     * @param depots the updated warehouse
     */
    @Override
    public void showUpdatedWarehouse(String nickname, List<Map<Resource, Integer>> depots) {
        localModel.getPlayer(nickname).setWareHouseDepots(depots);
    }

    /**
     * Method to update the strongbox received from the Model
     * @param nickname the nickname of the player that received the update
     * @param strongbox the updated strongbox
     */
    @Override
    public void showUpdatedStrongbox(String nickname, Map<Resource, Integer> strongbox) {
        localModel.getPlayer(nickname).setStrongbox(strongbox);
    }

    /**
     * Method to update the development card space received from the Model
     * @param nickname the nickname of the player that received the update
     * @param cardsState the updated development card space
     */
    @Override
    public void showUpdatedDevCardSpace(String nickname, ArrayList<ArrayList<Integer>> cardsState) {
        localModel.getPlayer(nickname).setDevelopmentCardSpace(cardsState);
    }

    /**
     * Method to update the temporary marbles received from the Model
     * @param nickname the nickname of the player that received the update
     * @param temporaryMarbles the updated temporary marbles
     */
    @Override
    public void showUpdateTemporaryMarbles(String nickname, Map<Marble, Integer> temporaryMarbles) {
        localModel.getPlayer(nickname).setTemporaryMarbles(temporaryMarbles);
        if(!temporaryMarbles.isEmpty()) {
            System.out.println(nickname + " obtained these marbles from the market:");
            localModel.getPlayer(nickname).printTermporaryMarbles();
        }
    }

    /**
     * Method to update the position of the red cross received from the Model
     * @param nickname the nickname of the player that received the update
     * @param redcrossPosition the updated position of the red cross
     */
    @Override
    public void showUpdateRedcrossPosition(String nickname, int redcrossPosition) {
        localModel.getPlayer(nickname).setRedCrossPosition(redcrossPosition);
        //System.out.println("redCross Updated");//this print id temporary
    }

    /**
     * Method to update the pope favour tiles values received from the Model
     * @param nickname the nickname of the player that received the update
     * @param popeFavourTiles the updated pope favour tiles values
     */
    @Override
    public void showUpdatePopeFavourTiles(String nickname, ArrayList<Integer> popeFavourTiles) {
        localModel.getPlayer(nickname).setPopeFavourTiles(popeFavourTiles);
    }

    /**
     * Method to update the player's turn received from the Model
     * @param nickname the nickname of the player that has to play the turn
     */
    @Override
    public void showUpdatePlayerTurn(String nickname) {
        if (nickname.equals(localModel.getLocalPlayer().getNickname())){
            System.out.println("It's your turn");
        }
        else {
            System.out.println("It's " + nickname + "'s turn");
        }


    }

    /**
     * Method to update the market received from the Model
     * @param marketMatrix the updated market
     * @param marbleOut the updated marble out
     */
    @Override
    public void showUpdateMarket(Marble[][] marketMatrix, Marble marbleOut) {
        localModel.setMarket(marketMatrix,marbleOut);
    }

    /**
     * Method to update the adding of a special depot received from the Model
     * @param nickname the nickname of the player that received the update
     * @param depotResourceType the resource type of the new depot
     */
    @Override
    public void showUpdateAddSpecialDepotUpdate(String nickname, Resource depotResourceType) {

    }

    /**
     * Method to update the card grid received from the Model
     * @param cardGridMatrixUpdate the updated card grid
     */
    @Override
    public void showUpdateCardGridUpdate(int[][] cardGridMatrixUpdate) {
        localModel.setCardGrid(cardGridMatrixUpdate);
    }

    /**
     * Method to update the discarded leader card from a player received from the model
     * @param nickname the nickname of the player that received the update
     * @param leaderPosition the index of the discarded leader card
     */
    @Override
    public void showUpdateDiscardedLeaderUpdate(String nickname, int leaderPosition) {
        localModel.removeLeaderCard(nickname, leaderPosition);
        System.out.println(nickname + " has discarded a leader card");
    }

    /**
     * Method to update the activation of a leader card received from the Model
     * @param nickname the nickname of the player that received the update
     * @param numLeadercard the index of the activated leader card
     * @param leaderCardID the ID of the activated leader card
     */
    @Override
    public void showUpdateLeaderCardActivatedUpdate(String nickname, int numLeadercard, int leaderCardID) {
        localModel.activeLeaderCard(nickname,numLeadercard,leaderCardID);
        System.out.println(nickname+ " has activated a leader card");
    }

    /**
     * Method to show the rank received from the Model at the end of the game
     * @param nickname the player that received the update
     * @param rank the rank generated at the end of the game
     */
    @Override
    public void showUpdateRank(String nickname, ArrayList<RankPosition> rank) {
        System.out.println("The game ended.\nFinal Rank:");
        for (int pos = 0;pos < rank.size();pos++){
            if (rank.get(pos).getNickname().equals(nickname))
                System.out.println((pos+1) + "° " + CliColor.COLOR_YELLOW + rank.get(pos).toString() + CliColor.RESET);
            else
                System.out.println((pos+1) + "° " + rank.get(pos).toString());
        }
    }

    /**
     * Method to update the position of Lorenzo's black cross received from the Model
     * @param blackCrossPosition the updated position of the black cross
     */
    @Override
    public void showUpdateBlackcrossPosition(int blackCrossPosition) {
        localModel.setBlackCrossPosition(blackCrossPosition);
    }

    /**
     * Method to show the card that lorenzo discarded from the card grid
     * @param nickname the nickname of the player that received the update
     * @param row the row in the card grid
     * @param column the column in the card grid
     */
    @Override
    public void showUpdateLorenzoDraw(String nickname, int row, int column) {
        System.out.println(nickname + " discarded a card from the card grid: [Row " + (row + 1) + ",Column " + (column + 1) + "]");
    }

    /**
     * Sends message to client
     * @param message Message notified by {@link ObservableGameEnder}
     */
    @Override
    public synchronized void update(MessageToClient message) {
        message.handleMessage(this);
    }

    /**
     * Getter for the nickname associated with the view
     * @return a nickname
     */
    @Override
    public String getNickname() {
        return localModel.getLocalPlayer().getNickname();
    }

    @Override
    public void setNickname(String nickname) {
        //TODO move nickname here and implement method
    }
}
