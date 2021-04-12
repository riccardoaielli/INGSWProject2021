package it.polimi.ingsw.model;

import java.util.Map;

public class GreyMarble extends Marble {
    private static GreyMarble instance = null;
    private final Integer value = 1;

    private GreyMarble(){}

    /**
     * Singleton pattern method to obtain the single instance
     * @return single GreyMarble instance of the game
     */
    public static GreyMarble getInstance() {
        if (instance == null) {
            instance = new GreyMarble();
        }
        return instance;
    }

    /**
     * Implementation of transform method, it transforms the marble into resource
     * @param resourceMap map of resources from personal board
     * @param faithTrack references of FaithTrack
     */
    public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack){

        resourceMap.merge(Stone.getInstance(), value, Integer::sum);

    }
}
