package it.polimi.ingsw.model.enumerations;

import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.model.exceptions.InvalidParameterException;

import java.util.Map;

/**
 * This class represents the abstract resource
 */
public enum Resource {
    COIN{
    },
    SHIELD{
    },
    SERVANT{
    },
    STONE{
    },
    FAITH{
        @Override
        public void dispatch(Map<Resource, Integer> resourceMap, FaithTrack faithTrack) {
            try {
                faithTrack.moveFaithMarker(resourceMap.get(FAITH));
            } catch (InvalidParameterException ignored) {
            }
            resourceMap.remove(FAITH);
        }
    };
    public void dispatch(Map<Resource, Integer> resourceMap, FaithTrack faithTrack){
    }
}
