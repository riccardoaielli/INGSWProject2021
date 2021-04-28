package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidParameterException;

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
     * Implementation of transform method, it moves the faithMarker of one position
     * @param resourceMap map of resources from personal board
     * @param faithTrack references of FaithTrack
     */
    public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack) throws InvalidParameterException {
        try {
            faithTrack.moveFaithMarker(value);
        }catch (Exception exception){
            throw new InvalidParameterException();
        }
    }
}
