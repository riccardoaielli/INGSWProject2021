package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.LeaderCard;
import it.polimi.ingsw.server.model.LeaderMarble;
import it.polimi.ingsw.server.model.Requirement;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.exceptions.InvalidLeaderAction;
import it.polimi.ingsw.server.model.exceptions.NotEnoughWhiteMarblesException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LeaderMarbleTest {

    @Test
    void abilityMarble() {

        Marble marbleTest = Marble.BLUEMARBLE;
        Requirement requirement = new Requirement(new ArrayList<>(),new HashMap<>());
        LeaderCard leaderCardTest = new LeaderMarble(10,requirement,marbleTest);

        HashMap<Marble,Integer> marblesTest = new HashMap<>();
        marblesTest.put(Marble.WHITEMARBLE,5);
        marblesTest.put(Marble.BLUEMARBLE,2);

        try {
            leaderCardTest.abilityMarble(marblesTest, 3);
            assertEquals(marblesTest.get(Marble.WHITEMARBLE), 2);
            assertEquals(marblesTest.get(Marble.BLUEMARBLE), 5);
            leaderCardTest.abilityMarble(marblesTest, 3);
        }catch (NotEnoughWhiteMarblesException exception){
            assert true;
        }
        catch (InvalidLeaderAction exception){
            assert false;
        }
        assertEquals(marblesTest.get(Marble.WHITEMARBLE), 2);
        assertEquals(marblesTest.get(Marble.BLUEMARBLE), 5);

    }
}