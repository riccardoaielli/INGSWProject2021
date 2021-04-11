package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrongboxTest {

    @Test
    void testAdd() {
        Strongbox strongbox1 = new Strongbox();
        //Testing adding a resource when such resource is not in strongbox
        strongbox1.add(Shield.getInstance(), 5);
        assertEquals(5, strongbox1.getResourceQuantity(Shield.getInstance()));
        strongbox1.add(Stone.getInstance(), 4);
        assertEquals(4, strongbox1.getResourceQuantity(Stone.getInstance()));
        strongbox1.add(Coin.getInstance(), 1);
        assertEquals(1, strongbox1.getResourceQuantity(Coin.getInstance()));
        strongbox1.add(Servant.getInstance(), 1000);
        assertEquals(1000, strongbox1.getResourceQuantity(Servant.getInstance()));


        //Testing adding a resource already present in strongbox
        strongbox1.add(Shield.getInstance(), 10);
        assertEquals(15, strongbox1.getResourceQuantity(Shield.getInstance()));
        strongbox1.add(Servant.getInstance(), 2000);
        assertEquals(3000, strongbox1.getResourceQuantity(Servant.getInstance()));
    }


    @Test
    void testRemove() {
        Strongbox strongbox1 = new Strongbox();
        //Testing the removal of fewer resources than there are in strongbox
        strongbox1.add(Stone.getInstance(), 10);
        strongbox1.remove(Stone.getInstance(), 6);
        assertEquals(4, strongbox1.getResourceQuantity(Stone.getInstance()));

        //Testing the removal of same resources than there are in strongbox
        strongbox1.remove(Stone.getInstance(), 4);
        assertEquals(0, strongbox1.getResourceQuantity(Stone.getInstance()));
    }

    @Test
    void testGetTotalResources(){
        Strongbox strongbox1 = new Strongbox();
        assertEquals(0, strongbox1.getTotalResources());

        strongbox1.add(Shield.getInstance(), 3);
        strongbox1.add(Stone.getInstance(), 2);
        strongbox1.add(Coin.getInstance(), 7);
        strongbox1.add(Servant.getInstance(), 3);
        assertEquals(15, strongbox1.getTotalResources());
    }

}