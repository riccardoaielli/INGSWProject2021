package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.FaithTrack;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MarbleTest {

    /**
     * Testing if the value of shield key in resourceMap is increased
     */
    @Test
    public void transformBlueTest(){
        Map<Resource,Integer> resourceMap = new HashMap<>();
        FaithTrack faithTrack = new FaithTrack();
        Marble blueMarble = Marble.BLUEMARBLE;

        try {
            blueMarble.transform(resourceMap, faithTrack);
        } catch (InvalidParameterException e) {
            assert false;
        }

        assertEquals(1, resourceMap.get(Resource.SHIELD));
    }

    /**
     * Testing if the value of stone key in resourceMap is increased
     */
    @Test
    public void transformGreyTest(){
        Map<Resource,Integer> resourceMap = new HashMap<>();
        FaithTrack faithTrack = new FaithTrack();
        Marble greyMarble = Marble.GREYMARBLE;

        try {
            greyMarble.transform(resourceMap, faithTrack);
        } catch (InvalidParameterException e) {
            assert false;
        }

        assertEquals(1, resourceMap.get(Resource.STONE));

    }

    /**
     * Testing if the value of servant key in resourceMap is increased
     */
    @Test
    public void transformPurpleTest(){
        Map<Resource,Integer> resourceMap = new HashMap<>();
        FaithTrack faithTrack = new FaithTrack();
        Marble purpleMarble = Marble.PURPLEMARBLE;

        try {
            purpleMarble.transform(resourceMap, faithTrack);
        } catch (InvalidParameterException e) {
            assert false;
        }

        assertEquals(1, resourceMap.get(Resource.SERVANT));
    }

    /**
     * Testing if the value of coin key in resourceMap is increased
     */
    @Test
    public void transformYellowTest(){
        Map<Resource,Integer> resourceMap = new HashMap<>();
        FaithTrack faithTrack = new FaithTrack();
        Marble yellowMarble = Marble.YELLOWMARBLE;

        try {
            yellowMarble.transform(resourceMap, faithTrack);
        } catch (InvalidParameterException e) {
            assert false;
        }

        assertEquals(1, resourceMap.get(Resource.COIN));
    }

}