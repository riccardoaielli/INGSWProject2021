package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WhiteMarbleTest {

    /**
     * Testing if the singleton returns the same instance
     */
    @Test
    public void instanceTest(){
        WhiteMarble instance1 = WhiteMarble.getInstance();
        WhiteMarble instance2 = WhiteMarble.getInstance();
        assertSame(instance1, instance2);
    }

}