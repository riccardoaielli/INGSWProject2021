package it.polimi.ingsw.model;

import java.util.Map;

public class PurpleMarble extends Marble {
    private static PurpleMarble instance = null;
    private final Integer value = 1;

    private PurpleMarble(){}

    /**
     * Singleton pattern method to obtain the single instance
     * @return single PurpleMarble instance of the game
     */
    public static PurpleMarble getInstance() {
        if (instance == null) {
            instance = new PurpleMarble();
        }
        return instance;
    }

    /**
     * Implementation of transform method, it transforms the marble into resource
     * @param resourceMap map of resources from personal board
     * @param faithTrack references of FaithTrack
     */
    public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack){

        resourceMap.merge(Servant.getInstance(), value, Integer::sum);

    }
}
