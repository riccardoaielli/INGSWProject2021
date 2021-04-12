package it.polimi.ingsw.model;

import java.util.Map;

public class RedMarble extends Marble {
    private static RedMarble instance = null;
    private final Integer value = 1;

    private RedMarble(){}

    /**
     * Singleton pattern method to obtain the single instance
     * @return single RedMarble instance of the game
     */
    public static RedMarble getInstance() {
        if (instance == null) {
            instance = new RedMarble();
        }
        return instance;
    }

    /**
     * Implementation of transform method, it moves the faithMarker
     * @param resourceMap map of resources from personal board
     * @param faithTrack references of FaithTrack
     */
    public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack){

        faithTrack.moveFaithMarker(value);

    }
}
