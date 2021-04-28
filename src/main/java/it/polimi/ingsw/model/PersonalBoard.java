package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidParameterException;
import it.polimi.ingsw.model.exceptions.NotEnoughWhiteMarblesException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PersonalBoard {
    private HashMap<Marble,Integer> temporaryMarbles;
    private ArrayList<PowerOfProduction> powerOfProductions;
    private ArrayList<LeaderCard> leaderCards;
    private FaithTrack faithTrack;
    private Match match;


    public void activateProduction(HashMap<Resource,Integer> costStrongbox, HashMap<Resource,Integer> costWarehouseDepot, PowerOfProduction powerOfProduction){

    }

    public void takeFromMarket(int rowOrColumn, int value){

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

    }

    public void addToWarehouseDepots(int depotLevel, HashMap<Resource,Integer> singleResourceMap){

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
