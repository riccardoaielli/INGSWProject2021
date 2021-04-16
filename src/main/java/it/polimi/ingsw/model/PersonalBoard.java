package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NotEnoughWhiteMarblesException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PersonalBoard {
    private HashMap<Marble,Integer> temporaryMarbles;
    private ArrayList<PowerOfProduction> powerOfProductions;
    private ArrayList<LeaderCard> leaderCards;


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

    private boolean checkVaticanReport(){
        return true;
    }

    public void notifyVaticanReport(){

    }

    private void checkLastRound(){

    }

    public int sumVictoryPoints(){
        return 0;
    }

    public void moveFaithMarker(){

    }

}
