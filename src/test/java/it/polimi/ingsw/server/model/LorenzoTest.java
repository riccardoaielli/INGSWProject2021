package it.polimi.ingsw.server.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.server.model.CardGrid;
import it.polimi.ingsw.server.model.Lorenzo;
import org.junit.jupiter.api.Test;

class LorenzoTest {

    CardGrid cardGrid = new CardGrid();
    Lorenzo lorenzo = new Lorenzo(cardGrid);

    @Test
    public void moveFaithMarkerTest(){

        lorenzo.moveFaithMarker(1);
        assertEquals(1, lorenzo.getFaithTrackPositionBlack());
        //testing maximum value of faith marker
        lorenzo.moveFaithMarker(21);
        assertEquals(20, lorenzo.getFaithTrackPositionBlack());

    }


}