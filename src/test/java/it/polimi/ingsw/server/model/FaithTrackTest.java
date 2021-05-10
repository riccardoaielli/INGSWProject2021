package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.FaithTrack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest {
    FaithTrack testFaithTrack = new FaithTrack();

    /**
     * This test tests moveFaithMarker method
     */
    @Test
    public void testFaithMarker(){
        try {
            //testing a standard value
            testFaithTrack.moveFaithMarker(3);
            assertEquals(3, testFaithTrack.getFaithTrackPosition());
            //testing maximum value of faith marker
            testFaithTrack.moveFaithMarker(18);
            assertEquals(20, testFaithTrack.getFaithTrackPosition());
        }catch (Exception exception) {
            assert false;
        }
        //testing negative values
        try {
            testFaithTrack.moveFaithMarker(-2);
            assertEquals(20, testFaithTrack.getFaithTrackPosition());
        }catch (Exception exception){
            assert true;
        }
    }

    /**
     * This test tests setPopeFavourTiles method in different position on the faithTrack
     */
    @Test
    public void  testFavourTiles(){
        try{
            //testing a tile out of the activation range
            testFaithTrack.setPopeFavourTiles(1);
            assertEquals(1, testFaithTrack.getPopeFavourTileValue(1));

            //testing a tile in the activation range
            testFaithTrack.moveFaithMarker(12);
            testFaithTrack.setPopeFavourTiles(2);
            assertEquals(2, testFaithTrack.getPopeFavourTileValue(2));
        }catch(Exception exception){
            assert false;
        }
        //testing invalid values
        try {
            testFaithTrack.setPopeFavourTiles(0);
        }
        catch (Exception exception){
            assert true;
        }

        try {
            testFaithTrack.setPopeFavourTiles(4);
        }
        catch (Exception exception){
            assert true;
        }
    }

    /**
     * This test tests the calculation of victory points form the faithTrack
     */
    @Test
    public void testCalculateVictoryPoints(){
        try {
            assertEquals(0, testFaithTrack.calculateVictoryPoints());
            testFaithTrack.moveFaithMarker(7);
            assertEquals(2, testFaithTrack.calculateVictoryPoints());

            testFaithTrack.setPopeFavourTiles(1);
            testFaithTrack.setPopeFavourTiles(2);
            assertEquals(4,testFaithTrack.calculateVictoryPoints());
        }catch (Exception exception){
            assert false;
        }
    }
}