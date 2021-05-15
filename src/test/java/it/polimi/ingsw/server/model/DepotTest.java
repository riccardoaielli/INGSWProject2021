package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Depot;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidAdditionException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DepotTest {
    @Test
    void testCheckResource() {
        Depot depot1 = new Depot(1);
        assertFalse(depot1.checkResource(Resource.SHIELD));
        Map<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Resource.SHIELD, 1);
        try {
            depot1.add(resourceMap);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        assertTrue(depot1.checkResource(Resource.SHIELD));

    }

    @Test
    void testAdd() {
        Depot depot1 = new Depot(3);
        Map<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Resource.SHIELD, 2);
        try {
            depot1.add(resourceMap);
        } catch (InvalidAdditionException e) {
            assert false;
        }

        //Testing addition when there is another resource in the depot
        resourceMap = new HashMap<>();
        resourceMap.put(Resource.COIN, 1);
        try {
            depot1.add(resourceMap);
            assert false;
        } catch (InvalidAdditionException e) {
            assertEquals("There is another resource in the depot", e.getMessage());
        }

        //Testing addition exceeding the maximum size of the depot
        depot1 = new Depot(3);

        //Adding the maximum permitted limit of Shield
        resourceMap = new HashMap<>();
        resourceMap.put(Resource.SHIELD, 3);
        try {
            depot1.add(resourceMap);
        } catch (InvalidAdditionException e) {
            assert false;
        }

        //Exceeding the limit of Shield in the depot
        resourceMap = new HashMap<>();
        resourceMap.put(Resource.SHIELD, 1);
        try {
            depot1.add(resourceMap);
        } catch (InvalidAdditionException e) {
            assertEquals("Not enough space", e.getMessage());
        }

    }

    @Test
    void getNumberResources() {
        Depot depot = new Depot(3);
        assertEquals(0, depot.getNumberResources());
        Map<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Resource.SERVANT, 3);
        try {
            depot.add(resourceMap);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        assertEquals(3, depot.getNumberResources());


    }

    @Test
    void uncheckedRemove() {
        Map<Resource, Integer> resourceMap1 = new HashMap<>();
        resourceMap1.put(Resource.COIN, 3);
        resourceMap1.put(Resource.SHIELD,4);

        Map<Resource, Integer> resourceMap2 = new HashMap<>();
        resourceMap2.put(Resource.COIN,2);

        Depot depot = new Depot(3);
        try {
            depot.add(resourceMap2);
        } catch (InvalidAdditionException e) {
            assert false;
        }

        depot.uncheckedRemove(resourceMap1);
        assertTrue(depot.getMapResource().isEmpty());
        assertEquals(1, resourceMap1.get(Resource.COIN));
        assertEquals(4, resourceMap1.get(Resource.SHIELD));
    }
}