package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest {
    FaithTrack testFaithTrack = new FaithTrack();

    @Test
    public void testConstructor(){
        assertEquals(0, testFaithTrack.getFaithTrackPosition());
        assertEquals(0, testFaithTrack.getPopeFavourTileValue(1));
        assertEquals(0, testFaithTrack.getPopeFavourTileValue(2));
        assertEquals(0, testFaithTrack.getPopeFavourTileValue(3));
    }

    @Test
    public void testFaithMarker(){
        testFaithTrack.moveFaithMarker(3);
        assertEquals(3, testFaithTrack.getFaithTrackPosition());
        //testing maximum value of faith marker
        testFaithTrack.moveFaithMarker(18);
        assertEquals(20, testFaithTrack.getFaithTrackPosition());
        //testing negative values
        try {
            testFaithTrack.moveFaithMarker(-2);
            assertEquals(20, testFaithTrack.getFaithTrackPosition());
        }catch (Error negativeNumOfSteps){
            assert true;
        }
    }

    @Test
    public void  testFavourTiles(){
        testFaithTrack.setPopeFavourTiles(1);
        assertEquals(1,testFaithTrack.getPopeFavourTileValue(1));

        testFaithTrack.moveFaithMarker(12);
        testFaithTrack.setPopeFavourTiles(2);
        assertEquals(2, testFaithTrack.getPopeFavourTileValue(2));

        try {
            testFaithTrack.setPopeFavourTiles(0);
        }
        catch (Error invalidTileNumber){
            assert true;
        }

        try {
            testFaithTrack.setPopeFavourTiles(4);
        }
        catch (Error invalidTileNumber){
            assert true;
        }
    }

    @Test
    public void testCalculateVictoryPoints(){
        assertEquals(0, testFaithTrack.calculateVictoryPoints());
        testFaithTrack.moveFaithMarker(7);
        assertEquals(2, testFaithTrack.calculateVictoryPoints());

        testFaithTrack.setPopeFavourTiles(1);
        testFaithTrack.setPopeFavourTiles(2);
        assertEquals(4,testFaithTrack.calculateVictoryPoints());
    }
}