package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Strongbox;
import it.polimi.ingsw.server.model.enumerations.Resource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StrongboxTest {

    @Test
    void testAdd() {
        Strongbox strongbox1 = new Strongbox();
        Map<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Resource.SHIELD, 5);
        resourceMap.put(Resource.STONE, 4);
        resourceMap.put(Resource.COIN, 1);
        resourceMap.put(Resource.SERVANT, 1000);
        strongbox1.add(resourceMap);

        //Testing adding a resource when such resource is not in strongbox
        assertEquals(5, strongbox1.getResourceQuantity(Resource.SHIELD));
        assertEquals(4, strongbox1.getResourceQuantity(Resource.STONE));
        assertEquals(1, strongbox1.getResourceQuantity(Resource.COIN));
        assertEquals(1000, strongbox1.getResourceQuantity(Resource.SERVANT));


        //Testing adding a resource already present in strongbox
        resourceMap.put(Resource.SHIELD, 10);
        resourceMap.put(Resource.SERVANT, 2000);
        strongbox1.add(resourceMap);
        assertEquals(15, strongbox1.getResourceQuantity(Resource.SHIELD));
        assertEquals(3000, strongbox1.getResourceQuantity(Resource.SERVANT));
    }


    @Test
    void testRemove() {
        Strongbox strongbox1 = new Strongbox();
        Map<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Resource.STONE, 10);
        //Testing the presence of a resource not in strongbox
        assertFalse(strongbox1.isAvailable(resourceMap));


        //Testing the removal of fewer resources than there are in strongbox

        //Adding 10 stones to strongbox1
        strongbox1.add(resourceMap);

        //removing 6 stones from strongbox1
        resourceMap = new HashMap<>();
        resourceMap.put(Resource.STONE, 6);

        if(strongbox1.isAvailable(resourceMap)){
            strongbox1.uncheckedRemove(resourceMap);
        }

        assertEquals(4, strongbox1.getResourceQuantity(Resource.STONE));

        //Testing the removal of same resources than there are in strongbox
        resourceMap = new HashMap<>();

        //removing 4 stones from strongbox1
        resourceMap.put(Resource.STONE, 4);

        if(strongbox1.isAvailable(resourceMap)){
            strongbox1.uncheckedRemove(resourceMap);
        }

        assertEquals(0, strongbox1.getResourceQuantity(Resource.STONE));

        //Testing the removal of more resources than there are in strongbox
        resourceMap = new HashMap<>();
        //adding 3 coins to strongbox
        resourceMap.put(Resource.COIN, 3);
        strongbox1.add(resourceMap);

        resourceMap.put(Resource.COIN, 10);
        assertFalse(strongbox1.isAvailable(resourceMap));
    }

    @Test
    void testGetTotalResources(){
        Strongbox strongbox1 = new Strongbox();
        assertEquals(0, strongbox1.getTotalResources());

        Map<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Resource.SHIELD, 3);
        resourceMap.put(Resource.STONE, 2);
        resourceMap.put(Resource.COIN, 7);
        resourceMap.put(Resource.SERVANT, 3);
        strongbox1.add(resourceMap);

        assertEquals(15, strongbox1.getTotalResources());
    }

    @Test
    void testResourceNotAvailable(){
        Strongbox strongbox1 = new Strongbox();
        Map<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Resource.STONE, 10);
        resourceMap.put(Resource.SERVANT, 2);
        strongbox1.add(resourceMap);
        Map<Resource, Integer> resourceMap2 = new HashMap<>();
        resourceMap2.put(Resource.COIN, 3);
        resourceMap2.put(Resource.SERVANT, 3);
        resourceMap2.put(Resource.STONE, 5);
        Map<Resource, Integer> resourceMap3;
        resourceMap3 = strongbox1.resourcesNotAvailable(resourceMap2);
        assertEquals(1, resourceMap3.get(Resource.SERVANT));
        assertEquals(3, resourceMap3.get(Resource.COIN));
        assertNull(resourceMap3.get(Resource.STONE));
    }

}