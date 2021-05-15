package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.View.VirtualView;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.MatchPhase;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidNickName;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
            match.addPlayer("marco", new ViewStub());
            match.addPlayer("mario",new ViewStub());
            assertEquals(match.getPlayer("marco").getNickname(),"marco");
            assertEquals(match.getPlayer("mario").getNickname(),"mario");
            //checks adding invalid nickname
            match.addPlayer("marco", new ViewStub());
        }catch (InvalidNickName exception){
            assert true;
        }
        assertEquals(match.getMatchPhase(), MatchPhase.SETUP);
        try {
            //check the change of match phase when the necessary number of player is reached
            match.addPlayer("massimo",new ViewStub());
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

    @Test
    public void vaticanReportTest(){
        Match match = null;
        try {
            match = new Match(1,2);
        } catch (InvalidParameterException invalidParameterException) {
            assert false;
        }

        try {
            match.addPlayer("marco",new ViewStub());
            match.addPlayer("mario",new ViewStub());
        }catch (InvalidNickName exception){
            assert false;
        }

        try {
            match.getPlayer("marco").getPersonalBoard().discardInitialLeader(1,2);
            match.getPlayer("mario").getPersonalBoard().discardInitialLeader(1,2);
        } catch (InvalidNickName | InvalidParameterException invalidNickName) {
            assert false;
        }

        Map<Resource,Integer> resourceMap =new HashMap<>();

        try {
            match.getCurrentPlayer().getPersonalBoard().addInitialResources(resourceMap);
            match.nextPlayer();
            resourceMap.put(Resource.SHIELD,1);
            match.getCurrentPlayer().getPersonalBoard().addInitialResources(resourceMap);
            match.nextPlayer();
        } catch (InvalidParameterException exception) {
            assert false;
        }


        try {
            match.getCurrentPlayer().getPersonalBoard().moveFaithMarker(2);
            match.getCurrentPlayer().getPersonalBoard().checkVaticanReport();

            assertEquals(2,match.getCurrentPlayer().getPersonalBoard().getFaithTrack().getFaithTrackPosition());
            assertEquals(0,match.getCurrentPlayer().getPersonalBoard().getFaithTrack().getPopeFavourTileValue(1));

            match.getCurrentPlayer().getPersonalBoard().moveFaithMarker(4);
            match.getCurrentPlayer().getPersonalBoard().checkVaticanReport();

            assertEquals(6,match.getCurrentPlayer().getPersonalBoard().getFaithTrack().getFaithTrackPosition());
            assertEquals(2,match.getCurrentPlayer().getPersonalBoard().getFaithTrack().getPopeFavourTileValue(1));
            match.nextPlayer();
            //checks the effect of the vatican report on other players

            assertEquals(0,match.getCurrentPlayer().getPersonalBoard().getFaithTrack().getFaithTrackPosition());
            assertEquals(1,match.getCurrentPlayer().getPersonalBoard().getFaithTrack().getPopeFavourTileValue(1));
        } catch (InvalidParameterException exception) {
            assert false;
        }
    }


}