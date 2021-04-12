package it.polimi.ingsw.model;

import java.util.Map;

public class YellowMarble extends Marble {
    private static YellowMarble instance = null;
    private final Integer value = 1;

    private YellowMarble(){}

    /**
     * Singleton pattern method to obtain the single instance
     * @return single YellowMarble instance of the game
     */
    public static YellowMarble getInstance() {
        if (instance == null) {
            instance = new YellowMarble();
        }
        return instance;
    }

    /**
     * Implementation of transform method, it transforms the marble into resource
     * @param resourceMap map of resources from personal board
     * @param faithTrack references of FaithTrack
     */
    public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack){

        resourceMap.merge(Coin.getInstance(), value, Integer::sum);

    }
}
