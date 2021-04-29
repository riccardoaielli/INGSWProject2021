package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class YellowMarbleTest {

    /**
     * Testing if the singleton returns the same instance
     */
    @Test
    public void instanceTest(){
        YellowMarble instance1 = YellowMarble.getInstance();
        YellowMarble instance2 = YellowMarble.getInstance();
        assertSame(instance1, instance2);
    }

    /**
     * Testing if the value of coin key in resourceMap is increased
     */
    @Test
    public void transformTest(){
        Map<Resource,Integer> resourceMap = new HashMap<>();
        FaithTrack faithTrack = new FaithTrack();
        YellowMarble yellowMarble = YellowMarble.getInstance();

        yellowMarble.transform(resourceMap, faithTrack);

        assertEquals(1, resourceMap.get(Resource.COIN));
    }

}