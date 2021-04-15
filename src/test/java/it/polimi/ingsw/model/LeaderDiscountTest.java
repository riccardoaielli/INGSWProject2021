package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LeaderDiscountTest {

    @Test
    void abilityDiscount() {
        Shield resourcediscount = Shield.getInstance();
        Requirement requirement = new Requirement(new ArrayList<>(),new HashMap<>());
        LeaderCard leaderCardTest = new LeaderDiscount(10,requirement,2,resourcediscount);

        HashMap<Resource,Integer> resourcesTest = new HashMap<>();
        resourcesTest.put(Shield.getInstance(),1);

        leaderCardTest.abilityDiscount(resourcesTest);
        assertTrue(resourcesTest.get(Shield.getInstance()) == 0);

        leaderCardTest.abilityDiscount(resourcesTest);
        assertTrue(resourcesTest.get(Shield.getInstance()) == 0);

        resourcesTest.put(Coin.getInstance(), 4);
        resourcesTest.put(Shield.getInstance(),3);
        leaderCardTest.abilityDiscount(resourcesTest);
        assertTrue(resourcesTest.get(Shield.getInstance()) == 1);
        assertTrue(resourcesTest.get(Coin.getInstance()) == 4);
    }
}