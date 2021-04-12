package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RedMarbleTest {

    /**
     * Testing if the singleton returns the same instance
     */
    @Test
    public void instanceTest(){
        RedMarble instance1 = RedMarble.getInstance();
        RedMarble instance2 = RedMarble.getInstance();
        assertSame(instance1, instance2);
    }

}