package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidAdditionException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DepotTest {
    @Test
    void testCheckResource() {
        Depot depot1 = new Depot(1);
        assertFalse(depot1.checkResource(Shield.getInstance()));
        HashMap<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Shield.getInstance(), 1);
        try {
            depot1.add(resourceMap);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        assertTrue(depot1.checkResource(Shield.getInstance()));

    }

    @Test
    void testAdd() {
        Depot depot1 = new Depot(3);
        HashMap<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Shield.getInstance(), 2);
        try {
            depot1.add(resourceMap);
        } catch (InvalidAdditionException e) {
            assert false;
        }

        //Testing addition when there is another resource in the depot
        resourceMap = new HashMap<>();
        resourceMap.put(Coin.getInstance(), 1);
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
        resourceMap.put(Shield.getInstance(), 3);
        try {
            depot1.add(resourceMap);
        } catch (InvalidAdditionException e) {
            assert false;
        }

        //Exceeding the limit of Shield in the depot
        resourceMap = new HashMap<>();
        resourceMap.put(Shield.getInstance(), 1);
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
        HashMap<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Servant.getInstance(), 3);
        try {
            depot.add(resourceMap);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        assertEquals(3, depot.getNumberResources());


    }

    @Test
    void uncheckedRemove() {
        HashMap<Resource, Integer> resourceMap1 = new HashMap<>();
        resourceMap1.put(Coin.getInstance(), 3);
        resourceMap1.put(Shield.getInstance(),4);

        HashMap<Resource, Integer> resourceMap2 = new HashMap<>();
        resourceMap2.put(Coin.getInstance(),2);

        Depot depot = new Depot(3);
        try {
            depot.add(resourceMap2);
        } catch (InvalidAdditionException e) {
            assert false;
        }

        depot.uncheckedRemove(resourceMap1);
        assertTrue(depot.getMapResource().isEmpty());
        assertEquals(1, resourceMap1.get(Coin.getInstance()));
        assertEquals(4, resourceMap1.get(Shield.getInstance()));
    }
}