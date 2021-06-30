package it.polimi.ingsw.server.model;

/**
 * This class represents the single player black cross (two steps forward) action token
 */
public class BlackCrossTwo extends SoloActionToken{
    Lorenzo lorenzo;
    final int numOfSteps = 2;

    public BlackCrossTwo(Lorenzo lorenzo){
        this.lorenzo = lorenzo;
    }

    /**
     * Implementation of soloAction method, it calls moveFaithMarker for the black cross (two step forward)
     */
    public void soloAction(){
        lorenzo.moveFaithMarker(numOfSteps);
    }
}
