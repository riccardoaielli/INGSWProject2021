package it.polimi.ingsw.server.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;

class LorenzoTest {
    @Test
    public void moveFaithMarkerTest(){
        SoloMatch match = null;
        {
            try {
                match = new SoloMatch(1,false);
            } catch (InvalidParameterException e) {
                assert false;
            }
        }
        Lorenzo lorenzo = new Lorenzo(match);
        try {
            lorenzo.addObserver(new SoloMatch(1, false));
        } catch (InvalidParameterException e) {
            assert false;
        }
        lorenzo.moveFaithMarker(1);
        assertEquals(1, lorenzo.getFaithTrackPositionBlack());
        //testing maximum value of faith marker
        lorenzo.moveFaithMarker(16);
        lorenzo.moveFaithMarker(28);
        assertEquals(24, lorenzo.getFaithTrackPositionBlack());
    }
}