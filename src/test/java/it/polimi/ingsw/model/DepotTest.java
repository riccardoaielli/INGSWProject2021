package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidAdditionException;
import it.polimi.ingsw.model.exceptions.InvalidRemovalException;
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
    void remove() {
        Depot depot1 = new Depot(3);
        HashMap<Resource, Integer> resourceMap1 = new HashMap<>();
        resourceMap1.put(Shield.getInstance(), 3);
        try {
            depot1.add(resourceMap1);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        resourceMap1.put(Shield.getInstance(), 2);
        try {
            depot1.remove(resourceMap1);
        } catch (InvalidRemovalException e) {
            assert false;
        }
        assertEquals(1, depot1.getMapResource().get(Shield.getInstance()));

        try {
            depot1.remove(resourceMap1);
        } catch (InvalidRemovalException e) {
            assert true;
        }
        HashMap<Resource, Integer> resourceMap2 = new HashMap<>();
        resourceMap2.put(Stone.getInstance(), 3);
        //Trying the removal of a resource not in depot
        try {
            depot1.remove(resourceMap2);
        } catch (InvalidRemovalException e) {
            assert true;
        }
        assertEquals(1, depot1.getMapResource().get(Shield.getInstance()));

        resourceMap1.put(Shield.getInstance(), 1);
        try {
            depot1.remove(resourceMap1);
        } catch (InvalidRemovalException e) {
            assert false;
        }
        assertNull(depot1.getMapResource().get(Shield.getInstance()));
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
}