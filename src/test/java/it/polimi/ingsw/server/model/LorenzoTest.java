package it.polimi.ingsw.server.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.server.model.exceptions.InvalidNickName;
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
        try {
            match.addPlayer("Pippo", new ViewStub());
        } catch (InvalidNickName invalidNickName) {
            assert false;
        }
        Lorenzo lorenzo = new Lorenzo(match);
        lorenzo.addObserver(match);

        lorenzo.moveFaithMarker(1);
        assertEquals(1, lorenzo.getFaithTrackPositionBlack());
        //testing maximum value of faith marker
        lorenzo.moveFaithMarker(16);
        lorenzo.moveFaithMarker(28);
        assertEquals(24, lorenzo.getFaithTrackPositionBlack());
    }
}