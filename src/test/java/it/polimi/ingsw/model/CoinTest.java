package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinTest {
    /**
     * Testing if the singleton returns the same instance
     */
    @Test
    public void instanceTest(){
        Coin instance1 = Coin.getInstance();
        Coin instance2 = Coin.getInstance();
        assertSame(instance1, instance2);
    }

}