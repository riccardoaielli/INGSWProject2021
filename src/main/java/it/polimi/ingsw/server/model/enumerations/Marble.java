package it.polimi.ingsw.server.model.enumerations;

import it.polimi.ingsw.server.model.FaithTrack;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;

import java.util.Map;

/**
 * This class represents the abstract marbles
 */
public enum Marble {


    WHITEMARBLE{

        /**
         * White marble doesn't transform into anything
         * @param resourceMap map of resources from personal board
         * @param faithTrack references of FaithTrack
         */
        @Override
        public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack){

        }
    },
    PURPLEMARBLE{

        /**
         * Implementation of transform method, it transforms the marble into resource
         * @param resourceMap map of resources from personal board
         * @param faithTrack references of FaithTrack
         */
        @Override
        public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack){

            resourceMap.merge(Resource.SERVANT, value, Integer::sum);

        }
    },
    BLUEMARBLE{

        /**
         * Implementation of transform method, it transforms the marble into resource
         * @param resourceMap map of resources from personal board
         * @param faithTrack references of FaithTrack
         */
        @Override
        public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack){

            resourceMap.merge(Resource.SHIELD, value, Integer::sum);

        }
    },
    YELLOWMARBLE{

        /**
         * Implementation of transform method, it transforms the marble into resource
         * @param resourceMap map of resources from personal board
         * @param faithTrack references of FaithTrack
         */
        @Override
        public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack){

            resourceMap.merge(Resource.COIN, value, Integer::sum);

        }
    },
    GREYMARBLE{

        /**
         * Implementation of transform method, it transforms the marble into resource
         * @param resourceMap map of resources from personal board
         * @param faithTrack references of FaithTrack
         */
        @Override
        public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack){

            resourceMap.merge(Resource.STONE, value, Integer::sum);

        }
    },
    REDMARBLE{

        /**
         * Implementation of transform method, it moves the faithMarker of one position
         * @param resourceMap map of resources from personal board
         * @param faithTrack references of FaithTrack
         */
        @Override
        public void transform (Map<Resource,Integer> resourceMap, FaithTrack faithTrack) throws InvalidParameterException {
            faithTrack.moveFaithMarker(value);

        }
    };

    private static final Integer value = 1;
    public abstract void transform (Map<Resource,Integer> temporaryMapResource, FaithTrack faithTrack) throws InvalidParameterException;

}
