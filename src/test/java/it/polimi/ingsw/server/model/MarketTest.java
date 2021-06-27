package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Marble;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    @Test
    void takeBoughtMarbles() {
        Market market = new Market(true);
        Map<Marble,Integer> testMarbles = new HashMap<>();

        testMarbles = market.takeBoughtMarbles(0,0);
        assertEquals(1,testMarbles.get(Marble.BLUEMARBLE));
        assertEquals(1,testMarbles.get(Marble.PURPLEMARBLE));
        assertEquals(1,testMarbles.get(Marble.REDMARBLE));
        assertEquals(1,testMarbles.get(Marble.WHITEMARBLE));
        assertEquals(Marble.WHITEMARBLE,market.getMarbleOut());

        testMarbles = market.takeBoughtMarbles(1,1);
        assertEquals(2,testMarbles.get(Marble.GREYMARBLE));
        assertEquals(1,testMarbles.get(Marble.PURPLEMARBLE));
        assertEquals(Marble.PURPLEMARBLE,market.getMarbleOut());
    }
}