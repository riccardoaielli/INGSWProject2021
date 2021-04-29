package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalBoard {
    private HashMap<Marble,Integer> temporaryMarbles;
    private HashMap<Resource, Integer> temporaryMapResource;
    private ArrayList<PowerOfProduction> powerOfProductions;
    private ArrayList<LeaderCard> leaderCards;
    private FaithTrack faithTrack;
    private Match match;
    private Strongbox strongbox;
    private WarehouseDepots warehouseDepots;
    private Market market;

    public PersonalBoard(ArrayList<LeaderCard> leaderCards, Match match) {
        //this.market = match.getMarket();
        //this.cardGrid = match.getCardGrid();
        this.leaderCards = leaderCards;
        this.faithTrack = new FaithTrack();
        this.match = match;
        this.strongbox = new Strongbox();
        this.warehouseDepots = new WarehouseDepots();
    }


    public void activateProduction(HashMap<Resource,Integer> costStrongbox, HashMap<Resource,Integer> costWarehouseDepot, int indexDevelopmentCardSpace){
        //Controllo che non sia già stato fatto
        //Mergiamo le due mappe cost in una temporanea e verifichiamo che sia uguale al cost del power of production del leader
        //Controllo disponibilità risorse
        //Rimuovo da stronbox/warehouse e aggiungo risorsa a strongbox/ punto fede
        //Cambio il flag della production in "fatto"
    }

    public void activateBasicProduction(HashMap<Resource,Integer> costStrongbox, HashMap<Resource,Integer> costWarehouseDepot, Resource resource){
        //Controllo che non sia già stato fatto
        //Controllo che la risorsa non sia fede
        //Controllo che ci siano effettivamente due risorse nei cost
        //Controllo disponibilità risorse checkAvailability()

        //Rimuovo da stronbox/warehouse e aggiungo risorsa a strongbox
        //Cambio il flag di basic production in "fatto"
    }

    public void activateLeaderProduction(HashMap<Resource,Integer> costStrongbox, HashMap<Resource,Integer> costWarehouseDepot, int indexLeaderCard, Resource resource){
        //Controllo che la leader card sia attiva
        //Controllo che non sia già stato fatto
        //Controllo che risorsa non sia fede
        //Ottengo power of produciton, se null lancio eccezione
        //Mergiamo le due mappe cost in una temporanea e verifichiamo che sia uguale al cost del power of production del leader
        //Controllo disponibilità risorse

        //Rimuovo da stronbox/warehouse e aggiungo risorsa a strongbox/ punto fede
        //Cambio il flag della leader production in "fatto"
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
    public void transformWhiteMarble(LeaderCard leaderCard, Integer numOfTransformations) throws NotEnoughWhiteMarblesException {
        try {
            leaderCard.abilityMarble(temporaryMarbles,numOfTransformations);
        } catch (NotEnoughWhiteMarblesException exception) {
            throw new NotEnoughWhiteMarblesException();
        }
    }

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
        try {
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
        }catch (InvalidParameterException invalidParameterException){
            invalidParameterException.printStackTrace();
        }
    }

    public void notifyVaticanReport(){

    }

    private void checkLastRound(){

    }

    public int sumVictoryPoints(){
        return 0;
    }

    /**
     * This method moves the faith marker and activates the vatican report if necessary
     * @param numOfSteps is the number of steps to make on the faith track
     * @throws InvalidParameterException
     */
    private void moveFaithMarkerInternally(int numOfSteps) throws InvalidParameterException {
        try {
            //moves the faith marker
            faithTrack.moveFaithMarker(numOfSteps);
            //activates checks vatican report
            checkVaticanReport();
        }catch (InvalidParameterException invalidParameterException){
            throw new InvalidParameterException();
        }
    }

    /**
     * This method moves the faith marker without checking for the vatican report
     * @param numOfSteps is the number of steps to make on the faith track
     * @throws InvalidParameterException
     */
    public void moveFaithMarker(int numOfSteps) throws InvalidParameterException {
        try {
            //moves the faith marker
            faithTrack.moveFaithMarker(numOfSteps);
        }catch (InvalidParameterException invalidParameterException){
            throw new InvalidParameterException();
        }
    }

    /**
     * This method activates the vatican report on a specific tile
     * @param tileNumber is the tile to activate
     */
    public void activateVaticanReport(int tileNumber){
        try{
            faithTrack.setPopeFavourTiles(tileNumber);
        }
        catch (InvalidParameterException invalidParameterException){
            invalidParameterException.printStackTrace();
        }
    }

}
