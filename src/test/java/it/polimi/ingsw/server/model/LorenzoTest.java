package it.polimi.ingsw.server.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.server.model.CardGrid;
import it.polimi.ingsw.server.model.Lorenzo;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;

class LorenzoTest {

    CardGrid cardGrid = new CardGrid();
    Lorenzo lorenzo = new Lorenzo(cardGrid);

    @Test
    public void moveFaithMarkerTest(){
        try {
            lorenzo.addObserver(new SoloMatch(1));
        } catch (InvalidParameterException e) {
            assert false;
        }
        lorenzo.moveFaithMarker(1);
        assertEquals(1, lorenzo.getFaithTrackPositionBlack());
        //testing maximum value of faith marker
        lorenzo.moveFaithMarker(21);
        assertEquals(20, lorenzo.getFaithTrackPositionBlack());

    }


}