package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.MatchPhase;
import it.polimi.ingsw.server.model.exceptions.InvalidNickName;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {


    @Test
    public void matchPhasesTest(){
        Match match = null;
        try {
            match = new Match(1,3);
            match = new Match(1,5);
        } catch (InvalidParameterException exception) {
            assert true;
        }

        assertEquals(match.getMatchPhase(), MatchPhase.SETUP);
        try {
            //checks nickname insertions
            match.addPlayer("marco");
            match.addPlayer("mario");
            assertEquals(match.getPlayer("marco").getNickname(),"marco");
            assertEquals(match.getPlayer("mario").getNickname(),"mario");
            //checks adding invalid nickname
            match.addPlayer("marco");
        }catch (InvalidNickName exception){
            assert true;
        }
        assertEquals(match.getMatchPhase(), MatchPhase.SETUP);
        try {
            //check the change of match phase when the necessary number of player is reached
            match.addPlayer("massimo");
            assertEquals(match.getMatchPhase(), MatchPhase.LEADERCHOICE);
        }catch (InvalidNickName exception){
            assert false;
        }

        try {
            match.getPlayer("marco").getPersonalBoard().discardInitialLeader(1,2);
            match.getPlayer("mario").getPersonalBoard().discardInitialLeader(1,2);
            assertEquals(match.getMatchPhase(), MatchPhase.LEADERCHOICE);
            match.getPlayer("massimo").getPersonalBoard().discardInitialLeader(1,2);
        } catch (InvalidNickName | InvalidParameterException invalidNickName) {
            assert false;
        }
        //check the change of match phase when the necessary number of player is reached
        assertEquals(match.getMatchPhase(), MatchPhase.RESOURCECHOICE);

        match.nextPlayer();
        match.nextPlayer();
        assertEquals(match.getMatchPhase(), MatchPhase.RESOURCECHOICE);
        match.nextPlayer();
        //check the change of match phase when all the players have played their first turn
        assertEquals(match.getMatchPhase(), MatchPhase.STANDARDROUND);

        Player p1 = match.getCurrentPlayer();
        match.nextPlayer();
        assertNotEquals(p1,match.getCurrentPlayer());
        try {
            match.getCurrentPlayer().getPersonalBoard().moveFaithMarker(30);
        } catch (InvalidParameterException invalidParameterException) {
            assert false;
        }
        //check the change of match phase when a player reaches the end of the faith track
        assertEquals(match.getMatchPhase(), MatchPhase.LASTROUND);

        match.nextPlayer();
        assertEquals(match.getMatchPhase(), MatchPhase.LASTROUND);
        match.nextPlayer();
        //check the change of match phase when the last player plays its last turn
        assertEquals(match.getMatchPhase(), MatchPhase.GAMEOVER);
    }




}