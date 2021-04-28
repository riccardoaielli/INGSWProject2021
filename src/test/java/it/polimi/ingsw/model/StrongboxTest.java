package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidRemovalException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StrongboxTest {

    @Test
    void testAdd() {
        Strongbox strongbox1 = new Strongbox();
        HashMap<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Shield.getInstance(), 5);
        resourceMap.put(Stone.getInstance(), 4);
        resourceMap.put(Coin.getInstance(), 1);
        resourceMap.put(Servant.getInstance(), 1000);
        strongbox1.add(resourceMap);

        //Testing adding a resource when such resource is not in strongbox
        assertEquals(5, strongbox1.getResourceQuantity(Shield.getInstance()));
        assertEquals(4, strongbox1.getResourceQuantity(Stone.getInstance()));
        assertEquals(1, strongbox1.getResourceQuantity(Coin.getInstance()));
        assertEquals(1000, strongbox1.getResourceQuantity(Servant.getInstance()));


        //Testing adding a resource already present in strongbox
        resourceMap.put(Shield.getInstance(), 10);
        resourceMap.put(Servant.getInstance(), 2000);
        strongbox1.add(resourceMap);
        assertEquals(15, strongbox1.getResourceQuantity(Shield.getInstance()));
        assertEquals(3000, strongbox1.getResourceQuantity(Servant.getInstance()));
    }


    @Test
    void testRemove() {
        Strongbox strongbox1 = new Strongbox();
        HashMap<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Stone.getInstance(), 10);
        //Testing the removal of a resource not in strongbox
        try {
            strongbox1.checkAvailability(resourceMap);
            strongbox1.uncheckedRemove(resourceMap);
        } catch (InvalidRemovalException e) {
            assert true;
        }
        //Testing the removal of fewer resources than there are in strongbox

        //Adding 10 stones to strongbox1
        strongbox1.add(resourceMap);

        //removing 6 stones from strongbox1
        resourceMap = new HashMap<>();
        resourceMap.put(Stone.getInstance(), 6);
        try {
            strongbox1.checkAvailability(resourceMap);
            strongbox1.uncheckedRemove(resourceMap);
        } catch (InvalidRemovalException e) {
            assert false;
        }
        assertEquals(4, strongbox1.getResourceQuantity(Stone.getInstance()));

        //Testing the removal of same resources than there are in strongbox
        resourceMap = new HashMap<>();

        //removing 4 stones from strongbox1
        resourceMap.put(Stone.getInstance(), 4);
        try {
            strongbox1.checkAvailability(resourceMap);
            strongbox1.uncheckedRemove(resourceMap);
        } catch (InvalidRemovalException e) {
            assert false;
        }

        assertEquals(0, strongbox1.getResourceQuantity(Stone.getInstance()));

        //Testing the removal of more resources than there are in strongbox
        resourceMap = new HashMap<>();
        //adding 3 coins to strongbox
        resourceMap.put(Coin.getInstance(), 3);
        strongbox1.add(resourceMap);

        resourceMap.put(Coin.getInstance(), 10);
        try {
            strongbox1.checkAvailability(resourceMap);
            strongbox1.uncheckedRemove(resourceMap);
            assert false;
        } catch (InvalidRemovalException e) {
            assert true;
        }


    }

    @Test
    void testGetTotalResources(){
        Strongbox strongbox1 = new Strongbox();
        assertEquals(0, strongbox1.getTotalResources());

        HashMap<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Shield.getInstance(), 3);
        resourceMap.put(Stone.getInstance(), 2);
        resourceMap.put(Coin.getInstance(), 7);
        resourceMap.put(Servant.getInstance(), 3);
        strongbox1.add(resourceMap);

        assertEquals(15, strongbox1.getTotalResources());
    }

}