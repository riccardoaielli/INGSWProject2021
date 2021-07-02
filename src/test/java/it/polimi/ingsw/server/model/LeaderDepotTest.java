package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidNickName;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;
import it.polimi.ingsw.server.model.exceptions.RequirementNotMetException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeaderDepotTest {

    @Test
    void abilityDepot() {
        LeaderDepot gameCard = null;
        LeaderDepot card = new LeaderDepot(1,null, Resource.COIN);
        try {
            Match match = new Match(1,1,true);
            match.addPlayer("Pippo",new ViewStub());
            match.addPlayerReady();
            match.getPlayer("Pippo").getPersonalBoard().activateLeader(1);
            gameCard = (LeaderDepot) match.getPlayer("Pippo").getPersonalBoard().getLeaderCards().get(0);
        } catch (InvalidParameterException | InvalidNickName | RequirementNotMetException e) {
            assert false;
        }
        assertEquals(card.getSpecialDepotResource(),gameCard.getSpecialDepotResource());
    }
}