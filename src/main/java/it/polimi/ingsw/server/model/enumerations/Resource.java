package it.polimi.ingsw.server.model.enumerations;

import it.polimi.ingsw.server.model.FaithTrack;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;

import java.util.Map;

/**
 * This class represents the abstract resource
 */
public enum Resource {
    COIN("COIN"),
    SHIELD("SHIELD"),
    SERVANT("SERVANT"),
    STONE("STONE"),
    FAITH("FAITH"){
        @Override
        public void dispatch(Map<Resource, Integer> resourceMap, FaithTrack faithTrack) {
            faithTrack.moveFaithMarker(resourceMap.get(FAITH));
            resourceMap.remove(FAITH);
        }
    };

    private String string;

    Resource(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    public void dispatch(Map<Resource, Integer> resourceMap, FaithTrack faithTrack){
    }
}
