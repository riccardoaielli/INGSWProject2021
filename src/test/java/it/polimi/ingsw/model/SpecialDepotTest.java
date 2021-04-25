package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidAdditionException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SpecialDepotTest {

    @Test
    void add() {
        SpecialDepot specialDepot1 = new SpecialDepot(Shield.getInstance());
        HashMap<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Coin.getInstance(), 1);
        try {
            specialDepot1.add(resourceMap);
            assert false;
        } catch (InvalidAdditionException e) {
            assertEquals("Resource not compatible with this special depot", e.getMessage());
        }
    }
}