package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GreyMarbleTest {

    /**
     * Testing if the singleton returns the same instance
     */
    @Test
    public void instanceTest(){
        GreyMarble instance1 = GreyMarble.getInstance();
        GreyMarble instance2 = GreyMarble.getInstance();
        assertSame(instance1, instance2);
    }

    /**
     * Testing if the value of stone key in resourceMap is increased
     */
    @Test
    public void transformTest(){
        Map<Resource,Integer> resourceMap = new HashMap<>();
        FaithTrack faithTrack = new FaithTrack();
        GreyMarble greyMarble = GreyMarble.getInstance();

        greyMarble.transform(resourceMap, faithTrack);

        assertEquals(1, resourceMap.get(Resource.STONE));
    }
}