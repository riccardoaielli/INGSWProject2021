package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NotEnoughWhiteMarblesException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LeaderMarbleTest {

    @Test
    void abilityMarble() {

        BlueMarble marbleTest = BlueMarble.getInstance();
        Requirement requirement = new Requirement(new ArrayList<CardsRequirement>(),new HashMap<Resource,Integer>());
        LeaderCard leaderCardTest = new LeaderMarble(10,requirement,marbleTest);

        HashMap<Marble,Integer> marblesTest = new HashMap<>();
        marblesTest.put(WhiteMarble.getInstance(),5);
        marblesTest.put(BlueMarble.getInstance(),2);

        try {
            leaderCardTest.abilityMarble(marblesTest, 3);
            assertEquals(marblesTest.get(WhiteMarble.getInstance()), 2);
            assertEquals(marblesTest.get(BlueMarble.getInstance()), 5);
            leaderCardTest.abilityMarble(marblesTest, 3);
        }catch (NotEnoughWhiteMarblesException exception){
            assert true;
        }
        assertEquals(marblesTest.get(WhiteMarble.getInstance()), 2);
        assertEquals(marblesTest.get(BlueMarble.getInstance()), 5);

    }
}