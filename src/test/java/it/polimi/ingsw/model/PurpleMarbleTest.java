package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PurpleMarbleTest {

    /**
     * Testing if the singleton returns the same instance
     */
    @Test
    public void instanceTest(){
        PurpleMarble instance1 = PurpleMarble.getInstance();
        PurpleMarble instance2 = PurpleMarble.getInstance();
        assertSame(instance1, instance2);
    }

    /**
     * Testing if the value of servant key in resourceMap is increased
     */
    @Test
    public void transformTest(){
        Map<Resource,Integer> resourceMap = new HashMap<>();
        FaithTrack faithTrack = new FaithTrack();
        PurpleMarble purpleMarble = PurpleMarble.getInstance();

        purpleMarble.transform(resourceMap, faithTrack);

        assertEquals(1, resourceMap.get(Resource.SERVANT));
    }

}