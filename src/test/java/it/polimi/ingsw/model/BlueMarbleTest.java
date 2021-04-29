package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BlueMarbleTest {

    /**
     * Testing if the singleton returns the same instance
     */
    @Test
    public void instanceTest(){
        BlueMarble instance1 = BlueMarble.getInstance();
        BlueMarble instance2 = BlueMarble.getInstance();
        assertSame(instance1, instance2);
    }

    /**
     * Testing if the value of shield key in resourceMap is increased
     */
    @Test
    public void transformTest(){
        Map<Resource,Integer> resourceMap = new HashMap<>();
        FaithTrack faithTrack = new FaithTrack();
        BlueMarble blueMarble = BlueMarble.getInstance();

        blueMarble.transform(resourceMap, faithTrack);

        assertEquals(1, resourceMap.get(Resource.SHIELD));
    }

}