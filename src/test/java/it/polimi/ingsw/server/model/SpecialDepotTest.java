package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.SpecialDepot;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidAdditionException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SpecialDepotTest {

    @Test
    void add() {
        SpecialDepot specialDepot1 = new SpecialDepot(Resource.SHIELD);
        HashMap<Resource, Integer> resourceMap = new HashMap<>();
        resourceMap.put(Resource.COIN, 1);
        try {
            specialDepot1.add(resourceMap);
            assert false;
        } catch (InvalidAdditionException e) {
            assertEquals("Resource not compatible with this special depot", e.getMessage());
        }
    }
}