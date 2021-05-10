package it.polimi.ingsw.server.model.enumerations;

import it.polimi.ingsw.server.model.FaithTrack;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;

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
