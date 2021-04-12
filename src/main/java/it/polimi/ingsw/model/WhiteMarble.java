package it.polimi.ingsw.model;

import java.util.Map;

public class WhiteMarble extends Marble {
    private static WhiteMarble instance = null;
    private WhiteMarble(){}

    /**
     * Singleton pattern method to obtain the single instance
     * @return single WhiteMarble instance of the game
     */
    public static WhiteMarble getInstance() {
        if (instance == null) {
            instance = new WhiteMarble();
        }
        return instance;
    }

    /**
     * White marble doesn't transform into anything
     * @param resourceMap map of resources from personal board
     * @param faithTrack references of FaithTrack
     */
    public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack){

    }

}
