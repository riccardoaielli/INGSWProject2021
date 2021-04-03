package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShieldTest {
    /**
     * Testing if the singleton returns the same instance
     */
    @Test
    public void instanceTest(){
        Shield instance1 = Shield.getInstance();
        Shield instance2 = Shield.getInstance();
        assertSame(instance1, instance2);
    }
}