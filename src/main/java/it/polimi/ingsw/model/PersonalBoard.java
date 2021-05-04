package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Marble;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PersonalBoard {
    private final int TOTPOWERPRODUCTIONS = 6;
    private int victoryPoints;
    private HashMap<Marble,Integer> temporaryMarbles;
    private HashMap<Resource, Integer> temporaryMapResource;
    private ArrayList<PowerOfProduction> powerOfProductions;
    private ArrayList<LeaderCard> leaderCards;
    private FaithTrack faithTrack;
    private Match match;
    private Strongbox strongbox;
    private WarehouseDepots warehouseDepots;
    private Market market;
    private CardGrid cardGrid;
    private DevelopmentCardSpace developmentCardSpace;
    //Array of booleans used to check if a powerOfProduction has already been used in the same turn
    private Boolean[] powerOfProductionUsed = new Boolean[TOTPOWERPRODUCTIONS];

    public PersonalBoard(ArrayList<LeaderCard> leaderCards, Match match) {
        this.market = match.getMarket();
        this.cardGrid = match.getCardGrid();
        this.leaderCards = leaderCards;
        this.faithTrack = new FaithTrack();
        this.match = match;
        this.strongbox = new Strongbox();
        this.warehouseDepots = new WarehouseDepots();

    }

    private void mergeCostsAndVerify(HashMap<Resource,Integer> costStrongbox, HashMap<Resource,Integer> costWarehouseDepot, PowerOfProduction powerOfProduction) throws InvalidProductionException {
        //Merging maps in a temporary cost map and checking it is equal to cost of power of production
        HashMap<Resource,Integer> totalCostResourceMap =  new HashMap<>(costStrongbox);
        costWarehouseDepot.forEach(
                (key, value) -> totalCostResourceMap.merge(key, value, Integer::sum)
        );

        if(!totalCostResourceMap.equals(powerOfProduction.getCost())){
            throw new InvalidProductionException();
        }
    }

    private void produce(HashMap<Resource,Integer> costStrongbox, HashMap<Resource,Integer> costWarehouseDepot, HashMap<Resource,Integer> production) throws InvalidRemovalException {
        //Checking resource availability
        strongbox.checkAvailability(costStrongbox);
        warehouseDepots.checkAvailability(costWarehouseDepot);
        //Removing price paid from strongbox and/or warehouse
        strongbox.uncheckedRemove(costStrongbox);
        warehouseDepots.uncheckedRemove(costWarehouseDepot);
        //Adding production to strongbox and/or faithTrack
        dispatch(production);
        strongbox.add(production);
    }

    //Method that is used to remove faith from temporaryMapResource and to add it to faithTrack
    private void dispatch(Map<Resource,Integer> production){
        for (Resource resource : production.keySet()){
            resource.dispatch(production, faithTrack);
        }
    }


    public void activateProduction(HashMap<Resource,Integer> costStrongbox, HashMap<Resource,Integer> costWarehouseDepot, int indexDevelopmentCardSpace) throws InvalidProductionException, InvalidRemovalException {
        //Checking that this production has not already been used in this turn
        if (powerOfProductionUsed[indexDevelopmentCardSpace]) {
            throw new InvalidProductionException();
        }
        PowerOfProduction powerOfProduction = developmentCardSpace.getPowerOfProduction().get(indexDevelopmentCardSpace-1);
        //Checking the correctness of costs
        mergeCostsAndVerify(costStrongbox, costWarehouseDepot, powerOfProduction);
        //Activating production
        produce(costStrongbox, costWarehouseDepot, powerOfProduction.getProduction());
        //Marking that the production has been done in this turn
        powerOfProductionUsed[indexDevelopmentCardSpace] = true;
    }

    public void activateBasicProduction(HashMap<Resource,Integer> costStrongbox, HashMap<Resource,Integer> costWarehouseDepot, Resource resource) throws InvalidProductionException, InvalidRemovalException {
        //Checking that this production has not already been used in this turn
        if (powerOfProductionUsed[0]) {
            throw new InvalidProductionException();
        }
        //Checking that resource is not faith
        if (resource == Resource.FAITH){
            throw new InvalidProductionException();
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
            throw new InvalidProductionException();
        }
        //Creating a resource map with the single resource
        HashMap<Resource, Integer> resourceToAdd = new HashMap<>();
        resourceToAdd.put(resource, 1);
        //Activating production
        produce(costStrongbox, costWarehouseDepot, resourceToAdd);
        //Marking that the production has been done in this turn
        powerOfProductionUsed[0] = true;
    }

    public void activateLeaderProduction(HashMap<Resource,Integer> costStrongbox, HashMap<Resource,Integer> costWarehouseDepot, int indexLeaderCard, Resource resource) throws InvalidProductionException, InvalidRemovalException, InvalidLeaderAction {
        //Checking that the power of production has not been already used in this turn
        if (powerOfProductionUsed[3+indexLeaderCard]){
            throw new InvalidProductionException();
        }
        LeaderCard leaderCard = leaderCards.get(indexLeaderCard-1);
        //Checking that leader card is active
        if(!leaderCard.isActive()){
            throw new InvalidProductionException();
        }
        //Checking that resource is not faith
        if (resource == Resource.FAITH) {
            throw new InvalidProductionException();
        }
        //Retrieving powerOfProduction of leaderCard, if not a production leader card an exception is thrown
        PowerOfProduction powerOfProduction = leaderCard.abilityProduction();
        if(powerOfProduction == null){
            throw new InvalidProductionException();
        }
        //Add resource chosen by player
        HashMap<Resource, Integer> production = powerOfProduction.getProduction();
        production.put(resource, 1);

        //checking that the resources the specified cost are right for this production
        mergeCostsAndVerify(costStrongbox, costWarehouseDepot, powerOfProduction);
        produce(costStrongbox, costWarehouseDepot, production);
        //Changing boolean of power of production for this turn
        powerOfProductionUsed[3+indexLeaderCard] = true;
    }



    public void takeFromMarket(int rowOrColumn, int value){
        temporaryMarbles = new HashMap<>(market.takeBoughtMarbles(rowOrColumn, value));
    }

    /**
     * This method transform a number of white marbles in the temporaryMarbles map
     * @param leaderCard is the card to use to transform marbles
     * @param numOfTransformations is the number of marbles that needs to be transformed
     * @throws NotEnoughWhiteMarblesException this exception is thrown when there are not enough white marbles in the given map of marbles
     */
    public void transformWhiteMarble(LeaderCard leaderCard, Integer numOfTransformations) throws NotEnoughWhiteMarblesException, InvalidLeaderAction {
        leaderCard.abilityMarble(temporaryMarbles,numOfTransformations);
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
    }

    public void addToWarehouseDepots(int depotLevel, HashMap<Resource,Integer> singleResourceMap) throws InvalidAdditionException {
        warehouseDepots.add(depotLevel, singleResourceMap);
    }

    public void swapResourceStandardDepot(int depot1, int depot2) throws InvalidSwapException {
        warehouseDepots.swap(depot1, depot2);
    }

    public void moveResourceSpecialDepot(int sourceDepotNumber, int destinationDepotNumber, int quantity) throws InvalidAdditionException, InvalidRemovalException, InvalidMoveException {
        warehouseDepots.moveToFromSpecialDepot(sourceDepotNumber, destinationDepotNumber, quantity);
    }

    public void buyDevelopmentCard(int row, int column,HashMap<Resource, Integer> costStrongbox, HashMap<Resource, Integer> costWarehouseDepots, LeaderCard leaderCard){

    }

    public void activateLeader(LeaderCard leaderCard){

    }

    public void removeLeader(LeaderCard leaderCard){

    }

    /**
     * This method checks the conditions to activate the vatican report and activates the report for all the players.
     */
    public void checkVaticanReport() {
        if (faithTrack.getPopeFavourTile(1) && 4 < faithTrack.getFaithTrackPosition() && faithTrack.getFaithTrackPosition() < 9){
            //calls the vatican report for all the other players
            match.vaticanReport(1);
        }
        else if(faithTrack.getPopeFavourTile(2) && 11 < faithTrack.getFaithTrackPosition() && faithTrack.getFaithTrackPosition() < 17){
            //calls the vatican report for all the other players
            match.vaticanReport(2);
        }
        else if(faithTrack.getPopeFavourTile(3) && 18 < faithTrack.getFaithTrackPosition() && faithTrack.getFaithTrackPosition() < 25){
            //calls the vatican report for all the other players
            match.vaticanReport(3);
        }
    }

    public void notifyVaticanReport(){

    }

    private void checkLastRound(){

    }

    public void sumVictoryPoints(){

    }

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

}
