package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaithTest {
    /**
     * Testing if the singleton returns the same instance
     */
    @Test
    public void instanceTest(){
        Faith instance1 = Faith.getInstance();
        Faith instance2 = Faith.getInstance();
        assertSame(instance1, instance2);
    }
}