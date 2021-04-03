package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServantTest {
    /**
     * Testing if the singleton returns the same instance
     */
    @Test
    public void instanceTest(){
        Servant instance1 = Servant.getInstance();
        Servant instance2 = Servant.getInstance();
        assertSame(instance1, instance2);
    }
}