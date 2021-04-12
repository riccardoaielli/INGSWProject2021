package it.polimi.ingsw.model;

import java.util.Map;

public class BlueMarble extends Marble {
    private static BlueMarble instance = null;
    private final Integer value = 1;

    private BlueMarble(){}

    /**
     * Singleton pattern method to obtain the single instance
     * @return single BlueMarble instance of the game
     */
    public static BlueMarble getInstance() {
        if (instance == null) {
            instance = new BlueMarble();
        }
        return instance;
    }

    /**
     * Implementation of transform method, it transforms the marble into resource
     * @param resourceMap map of resources from personal board
     * @param faithTrack references of FaithTrack
     */
    public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack){

        resourceMap.merge(Shield.getInstance(), value, Integer::sum);

    }
}
