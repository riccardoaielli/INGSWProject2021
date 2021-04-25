package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidAdditionException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.exceptions.InvalidSwapException;
import it.polimi.ingsw.model.exceptions.InvalidRemovalException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseDepotsTest {

    @Test
    void add() {
        WarehouseDepots warehouseDepots = new WarehouseDepots();
        HashMap<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Coin.getInstance(),2);
        resourceMap.put(Shield.getInstance(),1);
        try {
            warehouseDepots.add(2, resourceMap);
            assert false;
        } catch (InvalidAdditionException e) {
            assertEquals("More than one resource", e.getMessage());
        }
        //Removing the extra resource, now the resourceMap has only Coin
        resourceMap.remove(Shield.getInstance());

        //Adding coin to depot number 2
        try {
            warehouseDepots.add(2, resourceMap);
            assert true;
        } catch (InvalidAdditionException e) {
            assert false;
        }

        //Testing addition to a nonexistent depot
        try {
            warehouseDepots.add(4, resourceMap);
            assert false;
        } catch (InvalidAdditionException e) {
            assertEquals("Invalid depot", e.getMessage());
        }


        //Testing the same addition after adding a special depot and with the same resource in depot number 2
        warehouseDepots.addSpecialDepot(Coin.getInstance());
        try {
            warehouseDepots.add(4, resourceMap);
            assert true;
        } catch (InvalidAdditionException e) {
            assert false;
        }


        //Trying the addition of coin to a depot when the same resource is already in another depot
        try {
            warehouseDepots.add(3, resourceMap);
            assert false;
        } catch (InvalidAdditionException e) {
            assertEquals("Same resource in other depots", e.getMessage());
        }


    }

    @Test
    void addSpecialDepot() {
        WarehouseDepots warehouseDepots = new WarehouseDepots();
        assertEquals(3, warehouseDepots.getNumDepots());
        warehouseDepots.addSpecialDepot(Shield.getInstance());
        assertEquals(4, warehouseDepots.getNumDepots());

        HashMap<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Coin.getInstance(),2);
        try {
            warehouseDepots.add(4, resourceMap);
            assert false;
        } catch (InvalidAdditionException e) {
            assertEquals("Resource not compatible with this special depot", e.getMessage());
        }

    }

    @Test
    void swap() {
        WarehouseDepots warehouseDepots = new WarehouseDepots();
        HashMap<Resource, Integer> resourceMap1 = new HashMap<>();

        //Adding 2 coins to depot 2
        resourceMap1.put(Coin.getInstance(),2);
        try {
            warehouseDepots.add(2, resourceMap1);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        //Adding 2 stones to depot 3
        HashMap<Resource, Integer> resourceMap2 = new HashMap<>();
        resourceMap2.put(Stone.getInstance(),2);
        try {
            warehouseDepots.add(3, resourceMap2);
        } catch (InvalidAdditionException e) {
            assert false;
        }

        //Swapping resources in depot 2 and 3
        try {
            warehouseDepots.swap(2,3);
        } catch (InvalidSwapException e) {
            assert false;
        }

        //Checking if the resources have been swapped
        Depot depot2 = warehouseDepots.getDepot(2);
        assertEquals(2, depot2.getMapResource().get(Stone.getInstance()));
        Depot depot3 = warehouseDepots.getDepot(3);
        assertEquals(2, depot3.getMapResource().get(Coin.getInstance()));

        //Testing swapping when a resource do not fit a depot
        try {
            warehouseDepots.swap(2,1);
        } catch (InvalidSwapException e) {
            assert true;
        }

        //Testing swapping when a resource do not fit a depot
        try {
            warehouseDepots.swap(2,1);
        } catch (InvalidSwapException e) {
            assert true;
        }

        //Testing swapping when one of the depot is a special depot
        try {
            warehouseDepots.swap(3,4);
        } catch (InvalidSwapException e) {
            assert true;
        }
    }

    @Test
    void remove() {
        WarehouseDepots warehouseDepots = new WarehouseDepots();
        HashMap<Resource, Integer> resourceMap = new HashMap<>();

        //Trying to remove and empty resource map
        try {
            warehouseDepots.remove(2, resourceMap);
            assert false;
        } catch (InvalidRemovalException e) {
            assert true;
        }

        resourceMap.put(Shield.getInstance(),1);
        //Adding 1 shield to depot number 2
        try {
            warehouseDepots.add(2, resourceMap);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        //Checking if there is 1 shield
        assertEquals(1, warehouseDepots.getDepot(2).getMapResource().get(Shield.getInstance()));
        //Removing 1 shield from depot number 2
        try {
            warehouseDepots.remove(2, resourceMap);
        } catch (InvalidRemovalException e) {
            assert false;
        }
        //Checking there is not any shield in depot number 2
        assertNull(warehouseDepots.getDepot(2).getMapResource().get(Shield.getInstance()));

        //Removing from a non existent depot
        try {
            warehouseDepots.remove(4, resourceMap);
            assert false;
        } catch (InvalidRemovalException e) {
            assert true;
        }
    }

    @Test
    void getTotalResources() {
        WarehouseDepots warehouseDepots = new WarehouseDepots();
        //Checking the sum of the resources when the warehouse depot is empty
        assertEquals(0, warehouseDepots.getTotalResources());

        HashMap<Resource, Integer> resourceMap1 = new HashMap<>();
        resourceMap1.put(Servant.getInstance(), 1);
        HashMap<Resource, Integer> resourceMap2 = new HashMap<>();
        resourceMap2.put(Shield.getInstance(), 2);
        HashMap<Resource, Integer> resourceMap3 = new HashMap<>();
        resourceMap3.put(Stone.getInstance(), 3);
        //Adding 8 resources to warehouse depots
        try {
            warehouseDepots.add(1, resourceMap1);
            warehouseDepots.add(2, resourceMap2);
            warehouseDepots.add(3,resourceMap3);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        warehouseDepots.addSpecialDepot(Shield.getInstance());
        try {
            warehouseDepots.add(4, resourceMap2);
        } catch (InvalidAdditionException e) {
            assert  false;
        }
        //Checking that the sum is 8
        assertEquals(8, warehouseDepots.getTotalResources());
    }

    @Test
    void moveToFromSpecialDepot() {
        WarehouseDepots warehouseDepots = new WarehouseDepots();
        HashMap<Resource, Integer> resourceMap1 = new HashMap<>();
        resourceMap1.put(Coin.getInstance(), 3);
        //Putting 3 coins to depot 3
        try {
            warehouseDepots.add(3, resourceMap1);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        //Adding special depot
        warehouseDepots.addSpecialDepot(Coin.getInstance());
        //Adding 1 coin to special depot
        resourceMap1.put(Coin.getInstance(), 1);
        try {
            warehouseDepots.add(4, resourceMap1);
        } catch (InvalidAdditionException e) {
            assert false;
        }

        //Trying to move from an empty depot
        try {
            warehouseDepots.moveToFromSpecialDepot(2,4,1);
            assert false;
        } catch (InvalidRemovalException e) {
            assert true;
        } catch (InvalidAdditionException | InvalidMoveException e) {
            assert false;
        }

        //Moving 1 coin from depot 3 to depot 4
        try {
            warehouseDepots.moveToFromSpecialDepot(3,4,1);
        } catch (InvalidRemovalException | InvalidAdditionException | InvalidMoveException e) {
            assert false;
        }

        //Trying to move 1 coin from depot 3 to a non existent depot
        try {
            warehouseDepots.moveToFromSpecialDepot(3,5,1);
            assert false;
        } catch (InvalidRemovalException | InvalidAdditionException e) {
            assert false;
        } catch (InvalidMoveException e) {
            assert true;
        }

        //Trying to move a resource between two standard depots
        try {
            warehouseDepots.moveToFromSpecialDepot(2,3,1);
            assert false;
        } catch (InvalidRemovalException | InvalidAdditionException e) {
            assert false;
        } catch (InvalidMoveException e) {
            assert true;
        }

        try {
            warehouseDepots.moveToFromSpecialDepot(4,2,1);
            assert false;
        } catch (InvalidRemovalException | InvalidMoveException e) {
            assert false;
        } catch (InvalidAdditionException e) {
            assert true;
        }


        assertEquals(2, warehouseDepots.getDepot(3).getMapResource().get(Coin.getInstance()));
        assertEquals(2, warehouseDepots.getDepot(4).getMapResource().get(Coin.getInstance()));
    }
}