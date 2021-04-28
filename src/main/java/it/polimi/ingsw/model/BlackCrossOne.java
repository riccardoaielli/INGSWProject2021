package it.polimi.ingsw.model;

/**
 * This class represents the single player black cross (one steps forward, and shuffle the action tokens stack) action token
 */
public class BlackCrossOne extends SoloActionToken{

    Lorenzo lorenzo;
    final int numOfSteps = 1;

    public BlackCrossOne(Lorenzo lorenzo){

        this.lorenzo = lorenzo;

    }

    /**
     * Implementation of soloAction method, it calls moveFaithMarker for the black cross (one step forward), then it calls the lorenzo's shuffle method
     */
    public void soloAction(){

        lorenzo.moveFaithMarker(numOfSteps);
        lorenzo.shuffle();

    }
}
