package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.messages.messagesToClient.*;
import it.polimi.ingsw.common.utils.observe.MessageObservable;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.PersonalBoardPhase;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.*;

import java.util.*;
import java.util.Map;

public class PersonalBoard extends MessageObservable {
    private Player myPlayer;
    private final int TOTPOWERPRODUCTIONS = 6;
    private int victoryPoints;
    private Map<Marble,Integer> temporaryMarbles;
    private Map<Resource, Integer> temporaryMapResource;
    private ArrayList<LeaderCard> leaderCards;
    private FaithTrack faithTrack;
    private Match match;
    private Strongbox strongbox;
    private WarehouseDepots warehouseDepots;
    private Market market;
    private CardGrid cardGrid;
    private DevelopmentCardSpace developmentCardSpace;

    /*Array of booleans used to check if a powerOfProduction has already been used in the same turn
    Position 0: Basic Production
    Position 1,2,3: Development Card Production slot 1,2,3
    Position 4,5: Leader Card Production, card 1 and 2
     */
    private Boolean[] powerOfProductionUsed = new Boolean[TOTPOWERPRODUCTIONS];
    private PersonalBoardPhase personalBoardPhase;
    private int numOfResourcesToChoose;

    public PersonalBoard(ArrayList<LeaderCard> leaderCards, Match match) {
        this.addObserverList(match.getMessageObservers());
        this.setNickname(match.getNickname());

        this.victoryPoints = 0;
        this.market = match.getMarket();
        this.cardGrid = match.getCardGrid();
        this.leaderCards = leaderCards;

        this.faithTrack = new FaithTrack();
        faithTrack.addObserver(match);
        faithTrack.addObserverList(this.getMessageObservers());

        this.developmentCardSpace = new DevelopmentCardSpace();
        developmentCardSpace.addObserver(match);
        developmentCardSpace.addObserverList(this.getMessageObservers());

        this.match = match;

        this.strongbox = new Strongbox();
        strongbox.addObserverList(this.getMessageObservers());

        this.warehouseDepots = new WarehouseDepots();
        warehouseDepots.addObserverList(this.getMessageObservers());

        this.temporaryMapResource = new HashMap<>();
        this.temporaryMarbles = new HashMap<>();
        Arrays.fill(this.powerOfProductionUsed, false);
        numOfResourcesToChoose = 0;
        personalBoardPhase = PersonalBoardPhase.LEADER_CHOICE;
    }

    /**
     * Method used to set starting resources and cards for the player in a demo game
     */
    public void setDemo() {
        // settare faithtrack, risorse e carte sviluppo aggiunte in partenza per velocizzare la partita
        HashMap<Resource,Integer> demoResourceMap = new HashMap<>();
        demoResourceMap.put(Resource.COIN, 10);
        demoResourceMap.put(Resource.SHIELD, 10);
        demoResourceMap.put(Resource.SERVANT, 10);
        demoResourceMap.put(Resource.STONE, 10);
        strongbox.add(demoResourceMap);
        System.out.println("Demo player created");
    }


    //Method used to check if the merged maps of cost strongbox and cost warehouseDepot are equal to costToPay
    private void mergeCostsAndVerify(Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot, Map<Resource,Integer> costToPay) throws InvalidCostException {
        //Merging maps in a temporary cost map and checking it is equal to cost of power of production
        Map<Resource,Integer> totalCostResourceMap =  new HashMap<>(costStrongbox);
        costWarehouseDepot.forEach(
                (key, value) -> totalCostResourceMap.merge(key, value, Integer::sum)
        );

        if(!totalCostResourceMap.equals(costToPay)){
            throw new InvalidCostException("The specified cost is not valid");
        }
    }

    //Method used to remove resources from strongbox and warehouseDepot
    private void pay(Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot) throws InvalidRemovalException {
        //Checking resource availability
        if(!strongbox.isAvailable(costStrongbox) || !warehouseDepots.isAvailable(costWarehouseDepot)){
            throw new InvalidRemovalException("The are not enough resources");
        }
        //Removing price paid from strongbox and/or warehouse
        strongbox.uncheckedRemove(costStrongbox);
        warehouseDepots.uncheckedRemove(costWarehouseDepot);
    }

    //Method used to pay and add production to faithTrack and/or strongbox
    private void produce(Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot, Map<Resource,Integer> production) throws InvalidRemovalException {
        pay(costStrongbox, costWarehouseDepot);
        //Adding production to strongbox and/or faithTrack
        dispatch(production);
        checkVaticanReport();
        strongbox.add(production);
    }

    //Method that is used to remove faith from temporaryMapResource and to add it to faithTrack
    private void dispatch(Map<Resource,Integer> production){
        Set<Resource> productionSet = new HashSet<Resource>(production.keySet());
        for (Resource resource : productionSet){
            resource.dispatch(production, faithTrack);
        }
    }

    //Returns true if all the resources in resourceRequirement are present in strongbox and/or warehouse
    private boolean checkResourceRequirement(Map<Resource,Integer> resourceRequirement){
        Map<Resource,Integer> resourcesNotAvailable = warehouseDepots.resourcesNotAvailable(resourceRequirement);
        resourcesNotAvailable = strongbox.resourcesNotAvailable(resourcesNotAvailable);
        return resourcesNotAvailable.isEmpty();
    }

    /**
     * Method used to activate the production using the power of production of a development card
     * @param costStrongbox The cost of power of production paid with the resources located in the strongbox
     * @param costWarehouseDepot The cost of power of production paid with the resources located in the warehouse
     * @param indexDevelopmentCardSpace  The number of the development card slot used for the production. Ranges from 1 to 3
     * @throws InvalidProductionException If the same production has already been done in the same turn
     * @throws InvalidRemovalException If the payment can't be made
     * @throws InvalidCostException If the specified costs do not match the cost required by the power of production
     */
    public void activateCardProduction(Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot, int indexDevelopmentCardSpace) throws InvalidProductionException, InvalidRemovalException, InvalidCostException, InvalidParameterException {
        //Checking that this production has not already been used in this turn
        if (powerOfProductionUsed[indexDevelopmentCardSpace]) {
            throw new InvalidProductionException("You already used this production");
        }
        PowerOfProduction powerOfProduction = developmentCardSpace.getPowerOfProduction(indexDevelopmentCardSpace);
        //Checking the correctness of costs
        mergeCostsAndVerify(costStrongbox, costWarehouseDepot, powerOfProduction.getCost());
        //Activating production
        produce(costStrongbox, costWarehouseDepot, powerOfProduction.getProduction());
        //Marking that the production has been done in this turn
        powerOfProductionUsed[indexDevelopmentCardSpace] = true;
        personalBoardPhase = PersonalBoardPhase.PRODUCTION;
        myPlayer.getView().update(new ProductionDoneUpdate(getNickname()));
    }

    /**
     * Method used to activate the production using the basic production power
     * @param costStrongbox The cost of power of production paid with the resources located in the strongbox
     * @param costWarehouseDepot The cost of power of production paid with the resources located in the warehouse
     * @param resource The resource that is going to be produced
     * @throws InvalidProductionException If the same production has already been done in the same turn or if the specified resource is faith
     * @throws InvalidRemovalException If the payment can't be made
     * @throws InvalidCostException If the total resources are not two
     */
    public void activateBasicProduction(Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot, Resource resource) throws InvalidProductionException, InvalidRemovalException, InvalidCostException {
        //Checking that this production has not already been used in this turn
        if (powerOfProductionUsed[0]) {
            throw new InvalidProductionException("You already used this production");
        }
        //Checking that resource is not faith
        if (resource == Resource.FAITH){
            throw new InvalidProductionException("Resource produced can't be faith");
        }
        //Checking that there is a total of two resources in both costs
        int totalResources = 0;
        for (int singleQuantity : costStrongbox.values()){
            totalResources += singleQuantity;
        }
        for (int singleQuantity : costWarehouseDepot.values()){
            totalResources += singleQuantity;
        }
        if (totalResources != 2){
            throw new InvalidCostException("The specified cost is not valid");
        }
        //Creating a resource map with the single resource
        Map<Resource, Integer> resourceToAdd = new HashMap<>();
        resourceToAdd.put(resource, 1);
        //Activating production
        produce(costStrongbox, costWarehouseDepot, resourceToAdd);
        //Marking that the production has been done in this turn
        powerOfProductionUsed[0] = true;
        personalBoardPhase = PersonalBoardPhase.PRODUCTION;
        myPlayer.getView().update(new ProductionDoneUpdate(getNickname()));
    }

    /**
     * Method used to activate the production using the power of production of a leader card with the proper power
     * @param costStrongbox The cost of power of production paid with the resources located in the strongbox
     * @param costWarehouseDepot The cost of power of production paid with the resources located in the warehouse
     * @param numLeaderCard The number of the leader card to use, must be > 0 and < leader cards not discarded in PlayerBoard
     * @param resource The resource that is going to be produced (together with a faith point already provided by the leader card)
     * @throws InvalidProductionException If the specified leader card does not exist or if the same production has already been done in the same turn
     * @throws InvalidRemovalException If the payment can't be made
     * @throws InvalidLeaderAction If the chosen leader card does not have the proper power
     * @throws InvalidCostException If the specified costs do not match the cost required by the power of production
     */
    public void activateLeaderProduction(Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot, int numLeaderCard, Resource resource) throws InvalidProductionException, InvalidRemovalException, InvalidLeaderAction, InvalidCostException {
        //Checking that the specified leader card exists
        if (numLeaderCard <= 0 || numLeaderCard > leaderCards.size()){
            throw new InvalidProductionException("The leader card selected does not exist");
        }
        //Checking that the power of production has not been already used in this turn
        if (powerOfProductionUsed[3+numLeaderCard]){
            throw new InvalidProductionException("You already used this production");
        }
        LeaderCard leaderCard = leaderCards.get(numLeaderCard-1);
        //Checking that leader card is active
        if(!leaderCard.isActive()){
            throw new InvalidProductionException("The leader card selected is not active");
        }
        //Checking that resource is not faith
        if (resource == Resource.FAITH) {
            throw new InvalidProductionException("The resource to produce can't be faith");
        }
        //Retrieving powerOfProduction of leaderCard, if not a production leader card an exception is thrown
        PowerOfProduction powerOfProduction = leaderCard.abilityProduction();
        if(powerOfProduction == null){
            throw new InvalidProductionException("The power of production does not exist");
        }
        //Add resource chosen by player
        Map<Resource, Integer> production = new HashMap<>(powerOfProduction.getProduction());
        production.put(resource, 1);

        //checking that the resources the specified cost are right for this production
        mergeCostsAndVerify(costStrongbox, costWarehouseDepot, powerOfProduction.getCost());
        produce(costStrongbox, costWarehouseDepot, production);
        //Changing boolean of power of production for this turn
        powerOfProductionUsed[3+numLeaderCard] = true;
        personalBoardPhase = PersonalBoardPhase.PRODUCTION;
        myPlayer.getView().update(new ProductionDoneUpdate(getNickname()));
    }

    /**
     * Method used to acquire marbles form market
     * @param rowOrColumn 0 if row, 1 if column
     * @param value from 0 to 2 if row, from 0 to 3 if column Todo ricontrollare
     */
    public void takeFromMarket(int rowOrColumn, int value) throws InvalidParameterException {
        if ((rowOrColumn == 0 && value >= 0 && value <= 2) || (rowOrColumn == 1 && value >= 0 && value <= 3)){
            temporaryMarbles = new HashMap<>(market.takeBoughtMarbles(rowOrColumn, value));
            notifyObservers(new TemporaryMarblesUpdate(this.getNickname(), new HashMap<>(temporaryMarbles)));
        }
        else{
            throw new InvalidParameterException("Row or column are not valid");
        }
        personalBoardPhase = PersonalBoardPhase.TAKE_FROM_MARKET;
    }

    /**
     * This method transform a number of white marbles in the temporaryMarbles map
     * @param leaderCard is the number of the card to use to transform marbles
     * @param numOfTransformations is the number of marbles that needs to be transformed
     * @throws NotEnoughWhiteMarblesException this exception is thrown when there are not enough white marbles in the given map of marbles
     * @throws InvalidParameterException if the provided parameters contain not admitted values
     * @throws InvalidLeaderAction this exception is thrown when the selected leader does not have a suitable power
     */
    public void transformWhiteMarble(int leaderCard, int numOfTransformations) throws InvalidParameterException,NotEnoughWhiteMarblesException, InvalidLeaderAction {
        if (leaderCard > 0 && leaderCard <= leaderCards.size() && numOfTransformations > 0){
            leaderCards.get(leaderCard - 1).abilityMarble(temporaryMarbles, numOfTransformations);
            notifyObservers(new TemporaryMarblesUpdate(this.getNickname(), new HashMap<>(temporaryMarbles)));
        }
        else
            throw new InvalidParameterException("Invalid move: you don't ave this leader card or the asked marbles where negative ");
    }

    /**
     * Method used to transform marbles taken from market into resources stored in temporary map resources
     */
    public void transformMarbles(){
        for (Marble marble : temporaryMarbles.keySet()){
            for(int value = 0; value < temporaryMarbles.get(marble); value++){
                try {
                    marble.transform(temporaryMapResource, faithTrack);
                } catch (InvalidParameterException e) {
                    e.printStackTrace();
                }
            }
        }
        temporaryMarbles.clear();
        notifyObservers(new TemporaryMarblesUpdate(this.getNickname(), new HashMap<>(temporaryMarbles)));
        notifyObservers(new TemporaryResourceMapUpdate(this.getNickname(), new HashMap<>(temporaryMapResource)));

        //If every marble was white the temporaryMapResource is empty and the action is already done since there is nothing to add to warehouse
        if(temporaryMapResource.isEmpty()){
            personalBoardPhase = PersonalBoardPhase.MAIN_TURN_ACTION_DONE;
            myPlayer.getView().update(new MainTurnActionDoneUpdate(myPlayer.getNickname()));
        }
    }

    /**
     * Method used to add a single resource type in a specified quantity from the temporary resource map to a depot
     * @param depotLevel The depot to add the resource to
     * @param singleResourceMap The map which contains the single resource type and its quantity
     * @throws InvalidAdditionException If there is not a single resource type, if the resource is not in the temporary resource map or is not enough,
     * or if the rules of the warehouse depot are not followed
     */
    public void addToWarehouseDepots(int depotLevel, Map<Resource,Integer> singleResourceMap) throws InvalidAdditionException {
        //Checking if the request is correct
        if (singleResourceMap.size() != 1) {
            throw new InvalidAdditionException("Not one resource");
        }
        Resource resource = singleResourceMap.keySet().iterator().next();
        //Checking if there are enough resources in temporary map
        if(temporaryMapResource.get(resource) == null){
            throw new InvalidAdditionException("Not enough resources in temporary map");
        }
        if (temporaryMapResource.get(resource) < singleResourceMap.get(resource)){
            throw new InvalidAdditionException("Not enough resources in temporary map");
        }
        warehouseDepots.add(depotLevel, singleResourceMap);
        //Removing or subtracting from temporaryResourceMap
        if (temporaryMapResource.get(resource).equals(singleResourceMap.get(resource))){
            temporaryMapResource.remove(resource);
        }
        else{
            temporaryMapResource.put(resource, temporaryMapResource.get(resource) - singleResourceMap.get(resource));
        }
        notifyObservers(new TemporaryResourceMapUpdate(getNickname(), new HashMap<>(temporaryMapResource)));
        //if the temporaryResourceMap is empty and player is buying from the market the personalBoard phase changes
        if(temporaryMapResource.isEmpty()){
            if(personalBoardPhase == PersonalBoardPhase.TAKE_FROM_MARKET) {
                personalBoardPhase = PersonalBoardPhase.MAIN_TURN_ACTION_DONE;
                myPlayer.getView().update(new MainTurnActionDoneUpdate(myPlayer.getNickname()));
            }
            //Ends turn in the setup round before playing
            else if(personalBoardPhase == PersonalBoardPhase.ADD_INITIAL_RESOURCES){
                match.nextPlayer();
            }
        }
    }
    
    public void discardResourcesFromMarket(){
        int total = 0;
        for (int singleQuantity : temporaryMapResource.values()){
            total += singleQuantity;
        }

        temporaryMapResource.clear();
        notifyObservers(new TemporaryResourceMapUpdate(this.getNickname(), new HashMap<>(temporaryMapResource)));
        match.moveFaithMarkerAll(total);
        //personalBoardPhase = PersonalBoardPhase.MAIN_TURN_ACTION_DONE;
        if(personalBoardPhase == PersonalBoardPhase.TAKE_FROM_MARKET) {
            personalBoardPhase = PersonalBoardPhase.MAIN_TURN_ACTION_DONE;
            myPlayer.getView().update(new MainTurnActionDoneUpdate(myPlayer.getNickname()));
        }
        //Ends turn in the setup round before playing
        else if(personalBoardPhase == PersonalBoardPhase.ADD_INITIAL_RESOURCES){
            match.nextPlayer();
        }
    }

    /**
     * Method used to swap resources between two standard depots
     * @param depot1 Cannot be a special depot
     * @param depot2 Cannot be a special depot
     * @throws InvalidSwapException This exception is thrown when the depot is a special depot or when the resource of at least one depot do not fit the other depot
     */
    public void swapResourceStandardDepot(int depot1, int depot2) throws InvalidSwapException {
        warehouseDepots.swap(depot1, depot2);
    }

    /**
     * Method to move a resource between a standard depot and a special depot, only one of the two depots can be a special depot
     * @param sourceDepotNumber Number of the depot from which the resource has to be moved, if this is a special depot the other depot cannot be a special depot
     * @param destinationDepotNumber Number of the depot to which the resource has to be moved, if this is a special depot the other depot cannot be a special depot
     * @param quantity How much of the resource in the source depot has to be moved
     * @throws InvalidRemovalException If there is not enough of the resource in the source depot
     * @throws InvalidAdditionException If the resource cannot be moved to the destination depot
     * @throws InvalidMoveException If the condition that only one of the two depots can be a special depot is not respected or if at least one of the two depots does not exist
     */
    public void moveResourceSpecialDepot(int sourceDepotNumber, int destinationDepotNumber, int quantity) throws InvalidAdditionException, InvalidRemovalException, InvalidMoveException {
        warehouseDepots.moveToFromSpecialDepot(sourceDepotNumber, destinationDepotNumber, quantity);
    }

    //se index è 0 non attiva nessuna leader card, valori possibili 0,1 o 2

    /**
     * Method to buy a development card from card grid
     * @param row Row of the card grid of the chosen card, ranges from 1 to 3
     * @param column Column of the card grid of the chosen card, ranges from 1 to 4
     * @param costStrongbox The cost of power of production paid with the resources located in the strongbox
     * @param costWarehouseDepots The cost of power of production paid with the resources located in the warehouse
     * @param numLeaderCard The number of the leader card to use to discount the price, if 0 then no leader card will be used, otherwise must be > 0 and < leader cards not discarded in PlayerBoard
     * @param cardPosition The development card space slot in which the bought card will be placed
     * @throws NoCardException If there is no card in the specified coordinates of card grid
     * @throws InvalidCostException If the specified price does not match the one of the chosen card
     * @throws InvalidLeaderAction If the specified leader card does not have the proper power
     * @throws InvalidRemovalException If the payment can't be made
     * @throws InvalidDevelopmentCardException If the card cannot be placed in the chosen development card space slot
     * @throws InvalidParameterException If the specified development card space slot does not exist
     */
    public void buyDevelopmentCard(int row, int column,Map<Resource, Integer> costStrongbox, Map<Resource, Integer> costWarehouseDepots, int numLeaderCard, int cardPosition) throws NoCardException, InvalidCostException, InvalidLeaderAction, InvalidRemovalException, InvalidDevelopmentCardException, InvalidParameterException {
        DevelopmentCard cardToBuy = cardGrid.getCard(row - 1, column - 1);
        Map<Resource, Integer> price = new HashMap<>(cardToBuy.getPrice());
        //if numLeaderCard is 1 or 2 method tries to discount price
        if (numLeaderCard != 0){
            if (numLeaderCard < 0 || numLeaderCard > leaderCards.size())
                throw new InvalidLeaderAction("Invalid card");
            //if not the correct leader throws InvalidLeaderAction()
            leaderCards.get(numLeaderCard-1).abilityDiscount(price);
            //TODO make sure ability discount manages if resource to discount is not in price
        }
        //Verifying that the provided costs are correct
        mergeCostsAndVerify(costStrongbox, costWarehouseDepots, price);
        //Checking resource availability
        if(!strongbox.isAvailable(costStrongbox) || !warehouseDepots.isAvailable(costWarehouseDepots)){
            throw new InvalidRemovalException("The are not enough resources to purchase the card");
        }
        developmentCardSpace.addCard(cardToBuy, cardPosition);
        //Removing price paid from strongbox and/or warehouse
        strongbox.uncheckedRemove(costStrongbox);
        warehouseDepots.uncheckedRemove(costWarehouseDepots);
        cardGrid.buyCard(row - 1, column - 1);
        personalBoardPhase = PersonalBoardPhase.MAIN_TURN_ACTION_DONE;
        myPlayer.getView().update(new MainTurnActionDoneUpdate(myPlayer.getNickname()));
    }

    /**
     * Method to activate a leader
     * @param numLeaderCard The number of the leader card to activate, must be > 0 and < leader cards not discarded in PlayerBoard
     * @throws RequirementNotMetException if the requirements of the leader card are not met
     * @throws InvalidParameterException if the specified numLeaderCard is not correct, or if the leader card is already active
     */
    public void activateLeader(int numLeaderCard) throws RequirementNotMetException, InvalidParameterException {
        if (numLeaderCard <= 0 || numLeaderCard > leaderCards.size())
            throw new InvalidParameterException("Card not found");
        LeaderCard leaderCard = leaderCards.get(numLeaderCard-1);
        if (leaderCard.isActive()){
            throw new InvalidParameterException("The leader card is already active");
        }

        Requirement requirement = leaderCard.getRequirement();
        //Checking resource requirements if the field not set to null
        if (requirement.getResourceRequirement() != null){
            if(!checkResourceRequirement(requirement.getResourceRequirement())){
                throw new RequirementNotMetException("You don't have enough resources to activate this card");
            }
        }
        //Checking card requirements if the field is not set to null
        if (requirement.getCardsRequirement() != null){
            if(!developmentCardSpace.checkRequirement(requirement.getCardsRequirement())){
                throw new RequirementNotMetException("You don't have the necessary development cards to activate this card");
            }
        }
        //Activate leader card
        leaderCard.activate();
        leaderCard.abilityDepot(warehouseDepots);

        warehouseDepots.doNotify();
        notifyObservers(new LeaderCardActivatedUpdate(this.getNickname(), numLeaderCard, leaderCard.getId()));
    }

    /**
     * Method used to discard a leader card during the game and gain a faith point as a consequence
     * @param numLeaderCard The number of the leader card to activate, must be > 0 and < leader cards not discarded in PlayerBoard
     * @throws InvalidParameterException if numLeaderCard is incorrect or the leader does not exist
     */
    public void removeLeader(int numLeaderCard) throws InvalidParameterException {
        if (numLeaderCard <= 0 || numLeaderCard > leaderCards.size())
            throw new InvalidParameterException("The card can't be removed because you don't have this card");
        LeaderCard leaderCard = leaderCards.get(numLeaderCard-1);
        //Checking if leader card is active
        if (leaderCard.isActive()){
            throw new InvalidParameterException("The card can't be removed because the card is active");
        }
        //Moving faith
        moveFaithMarkerInternally(1);
        //Removing leader card
        leaderCards.remove(numLeaderCard-1);
        notifyObservers(new DiscardedLeaderUpdate(getNickname(), numLeaderCard));
    }

    /**
     * Method used to discard the two leader cards at the beginning of the match
     * @param indexLeaderCard1 Index of one of the two leader cards to discard, must range from 1 to 4 and must be different from the other index
     * @param indexLeaderCard2 Index of one of the two leader cards to discard, must range from 1 to 4 and must be different from the other index
     * @throws InvalidParameterException If the conditions specified in the parameters are not met
     */
    public void discardInitialLeader(int indexLeaderCard1, int indexLeaderCard2) throws InvalidParameterException {
        if (indexLeaderCard1 <= 0 || indexLeaderCard1 > leaderCards.size()
                || indexLeaderCard2 <= 0 || indexLeaderCard2 > leaderCards.size()
                || indexLeaderCard1 == indexLeaderCard2 )
            throw new InvalidParameterException("You don't have one of the two cards selected");
        List<Integer> indexesLeaderCard = new ArrayList<>();
        indexesLeaderCard.add(indexLeaderCard1);
        indexesLeaderCard.add(indexLeaderCard2);
        indexesLeaderCard.sort(Collections.reverseOrder());
        for (int indexLeaderCard: indexesLeaderCard){
            leaderCards.remove(indexLeaderCard-1);
        }
        personalBoardPhase = PersonalBoardPhase.RESOURCE_CHOICE;
        notifyObservers(new InitialLeaderDiscardedUpdate(myPlayer.getNickname(), indexLeaderCard1, indexLeaderCard2));
        match.addPlayerReady();
    }

    /**
     * Method used to add initial resources chosen by player according to its order
     * @param initialResources Resources to add to the temporary resources
     * @throws InvalidParameterException If the number of resources chosen doesn't match the possible number of resources the player can choose
     */
    public void addInitialResources(Map<Resource, Integer> initialResources) throws InvalidParameterException {
        int totalResources = 0;
        for(int singleResourceQuantity : initialResources.values()){
            totalResources+= singleResourceQuantity;
        }
        if (totalResources != numOfResourcesToChoose){
            throw new InvalidParameterException("You have to add " + numOfResourcesToChoose + " resources to your depot");
        }
        temporaryMapResource = new HashMap<>(initialResources);
        //Notifying observers that temporary map resource has changed
        notifyObservers(new TemporaryResourceMapUpdate(myPlayer.getNickname(), new HashMap<>(temporaryMapResource)));
        personalBoardPhase = PersonalBoardPhase.ADD_INITIAL_RESOURCES;
    }

    /**
     * This method is used when the player decides to end the production
     */
    public void endProduction(){
        strongbox.add(temporaryMapResource);
        temporaryMapResource.clear();
        notifyObservers(new TemporaryResourceMapUpdate(getNickname(), new HashMap<>(temporaryMapResource)));
        personalBoardPhase = PersonalBoardPhase.MAIN_TURN_ACTION_DONE;
    }

    /**
     * This method checks the conditions to activate the vatican report and activates the report for all the players.
     */
    public void checkVaticanReport() {
        if (faithTrack.getPopeFavourTile(1) && !faithTrack.getPopeFavourTile(2) && faithTrack.getFaithTrackPosition() > 7){
            //calls the vatican report for all the other players
            match.vaticanReport(1);
        }
        else if(faithTrack.getPopeFavourTile(2) && !faithTrack.getPopeFavourTile(3) && faithTrack.getFaithTrackPosition() > 15){
            //calls the vatican report for all the other players
            match.vaticanReport(2);
        }
        else if(faithTrack.getPopeFavourTile(3) && faithTrack.getFaithTrackPosition() == 24){
            //calls the vatican report for all the other players
            match.vaticanReport(3);
        }
    }

    /**
     * Calculates the victory points for this personalBoard
     */
    public void sumVictoryPoints(){
        //Points from development cards
        victoryPoints += developmentCardSpace.getVictoryPoints();
        //Points from faith points and Pope's favour tiles
        victoryPoints += faithTrack.calculateVictoryPoints();
        //Points from active leader cards
        for (LeaderCard leaderCard : leaderCards){
            if (leaderCard.isActive()){
                victoryPoints += leaderCard.getVictoryPoints();
            }
        }
        //Points from resources in Warehouse or Strongbox
        victoryPoints += Math.floorDiv(strongbox.getTotalResources() + warehouseDepots.getTotalResources(), 5);
    }

    /**
     * @return The victory points of this personalBoard
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * This method moves the faith marker and activates the vatican report if necessary
     * @param numOfSteps is the number of steps to make on the faith track
     * @throws InvalidParameterException when numOfSteps is negative
     */
    private void moveFaithMarkerInternally(int numOfSteps) throws InvalidParameterException {
        //moves the faith marker
        faithTrack.moveFaithMarker(numOfSteps);
        //activates checks vatican report
        checkVaticanReport();
    }

    /**
     * This method moves the faith marker without checking for the vatican report
     * @param numOfSteps is the number of steps to make on the faith track
     * @throws InvalidParameterException when numOfSteps is negative
     */
    public void moveFaithMarker(int numOfSteps) throws InvalidParameterException {
        //moves the faith marker
        faithTrack.moveFaithMarker(numOfSteps);
    }

    /**
     * This method activates the vatican report on a specific tile
     * @param tileNumber is the tile to activate
     */
    public void activateVaticanReport(int tileNumber){
        faithTrack.setPopeFavourTiles(tileNumber);
    }

    /**
     * @return The temporary resource map of the resources not yet stored
     */
    public Map<Resource, Integer> getTemporaryMapResource() {
        return temporaryMapResource;
    }
    /**
     * @return The temporary marble map of the marbles not yet transformed
     */
    public Map<Marble, Integer> getTemporaryMarbles() {
        return temporaryMarbles;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    public WarehouseDepots getWarehouseDepots() {
        return warehouseDepots;
    }

    public CardGrid getCardGrid() {
        return cardGrid;
    }

    public DevelopmentCardSpace getDevelopmentCardSpace() {
        return developmentCardSpace;
    }

    public PersonalBoardPhase getPersonalBoardPhase() {
        return personalBoardPhase;
    }

    public void setNumOfResourcesToChoose(int numOfResourcesToChoose) {
        this.numOfResourcesToChoose = numOfResourcesToChoose;
    }

    /**
     * this method is used only for testing purpose
     * @return the faith track of teh personal board
     */
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public void setPlayer(Player myPlayer){
        this.myPlayer = myPlayer;
        this.setNickname(myPlayer.getNickname());
        faithTrack.setNickname(this.getNickname());
        developmentCardSpace.setNickname(this.getNickname());
        strongbox.setNickname(this.getNickname());
        warehouseDepots.setNickname(this.getNickname());
    }

    /**
     * Notifies the view with a clone of the leader card in the model
     */
    public void doNotifyLeaders() {
        ArrayList<Integer> initialLeaderCardsID = new ArrayList<>();
        leaderCards.forEach(x->initialLeaderCardsID.add(x.getId()));
        if(myPlayer.getView() != null)
            myPlayer.getView().update(new InitialLeaderCardsUpdate(myPlayer.getNickname(),initialLeaderCardsID));
    }

    public void endTurn(){
        Arrays.fill(this.powerOfProductionUsed, false);
        personalBoardPhase = PersonalBoardPhase.MAIN_TURN_ACTION_AVAILABLE;
    }

    public void setPersonalBoardPhase(PersonalBoardPhase personalBoardPhase) {
        this.personalBoardPhase = personalBoardPhase;
    }

    /**
     * Getter for leader cards of the personal board
     * To use only for testing
     * @return a list of leader cards
     */
    public ArrayList<LeaderCard> getLeaderCards(){
        return leaderCards;
    }
}
