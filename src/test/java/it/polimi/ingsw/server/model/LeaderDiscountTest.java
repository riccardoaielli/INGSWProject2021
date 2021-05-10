package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.LeaderCard;
import it.polimi.ingsw.server.model.LeaderDiscount;
import it.polimi.ingsw.server.model.Requirement;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidLeaderAction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LeaderDiscountTest {

    @Test
    void abilityDiscount() {
        Resource resourcediscount = Resource.SHIELD;
        Requirement requirement = new Requirement(new ArrayList<>(),new HashMap<>());
        LeaderCard leaderCardTest = new LeaderDiscount(10,requirement,2,resourcediscount);

        HashMap<Resource,Integer> resourcesTest = new HashMap<>();
        resourcesTest.put(Resource.SHIELD,1);
        try {
            leaderCardTest.abilityDiscount(resourcesTest);
            assertTrue(resourcesTest.get(Resource.SHIELD) == 0);

            leaderCardTest.abilityDiscount(resourcesTest);
            assertTrue(resourcesTest.get(Resource.SHIELD) == 0);

            resourcesTest.put(Resource.COIN, 4);
            resourcesTest.put(Resource.SHIELD,3);
            leaderCardTest.abilityDiscount(resourcesTest);
            assertTrue(resourcesTest.get(Resource.SHIELD) == 1);
            assertTrue(resourcesTest.get(Resource.COIN) == 4);
        }catch (InvalidLeaderAction exception){
            assert false;
        }
    }
}