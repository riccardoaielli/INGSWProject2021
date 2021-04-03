package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StoneTest {
    /**
     * Testing if the singleton returns the same instance
     */
    @Test
    public void instanceTest(){
        Stone instance1 = Stone.getInstance();
        Stone instance2 = Stone.getInstance();
        assertSame(instance1, instance2);
    }

}
