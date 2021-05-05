package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Marble;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.InvalidAdditionException;
import it.polimi.ingsw.model.exceptions.InvalidNickName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PersonalBoardTest {
    PersonalBoard personalBoard;
    Match match;

    @BeforeEach
    void setup(){
        match = new Match(1, 4);
        try {
            match.addPlayer("Mario");
        } catch (InvalidNickName invalidNickName) {
            assert false;
        }

        try {
            personalBoard = match.getPlayer("Mario").getPersonalBoard();
        } catch (InvalidNickName invalidNickName) {
            assert false;
        }

    }

    @Test
    void activateProduction() {
    }

    @Test
    void activateBasicProduction() {
    }

    @Test
    void activateLeaderProduction() {
    }

    @Test
    void takeFromMarket() {
    }

    @Test
    void transformWhiteMarble() {
    }

    @Test
    void transformMarbles() {
        HashMap<Marble, Integer> personalBoardTemporaryMarbles = personalBoard.getTemporaryMarbles();
        personalBoardTemporaryMarbles.put(Marble.BLUEMARBLE, 2);
        personalBoardTemporaryMarbles.put(Marble.REDMARBLE, 1);
        personalBoardTemporaryMarbles.put(Marble.WHITEMARBLE, 1);
        personalBoard.transformMarbles();
        HashMap<Resource, Integer> personalBoardTemporaryMapResource = personalBoard.getTemporaryMapResource();
        assertEquals(2, personalBoardTemporaryMapResource.get(Resource.SHIELD));
        assertNull(personalBoardTemporaryMapResource.get(Resource.FAITH));
    }

    @Test
    void addToWarehouseDepots() {
        HashMap<Resource, Integer> personalBoardTemporaryMapResource = personalBoard.getTemporaryMapResource();
        HashMap<Resource, Integer> singleMapResource1 = new HashMap<>();
        HashMap<Resource, Integer> singleMapResource2 = new HashMap<>();
        personalBoardTemporaryMapResource.put(Resource.SHIELD, 3);
        personalBoardTemporaryMapResource.put(Resource.COIN, 1);
        //Adding 1 coin to depot 1
        singleMapResource1.put(Resource.COIN, 1);
        try {
            personalBoard.addToWarehouseDepots(1, singleMapResource1);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        //Adding 2 shields to depot 2
        singleMapResource2.put(Resource.SHIELD, 2);
        try {
            personalBoard.addToWarehouseDepots(2, singleMapResource2);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        //Testing edge cases
        assertThrows(InvalidAdditionException.class,() -> personalBoard.addToWarehouseDepots(2, singleMapResource2));
        assertThrows(InvalidAdditionException.class,() -> personalBoard.addToWarehouseDepots(3, new HashMap<>()));
        singleMapResource2.put(Resource.SERVANT, 2);
        assertThrows(InvalidAdditionException.class,() -> personalBoard.addToWarehouseDepots(2, singleMapResource2));
        //Verifying the changes of temporary map resource
        assertEquals(1, personalBoard.getTemporaryMapResource().get(Resource.SHIELD));
        assertNull(personalBoard.getTemporaryMapResource().get(Resource.COIN));
        assertNull(personalBoard.getTemporaryMapResource().get(Resource.SERVANT));
    }

    @Test
    void swapResourceStandardDepot() {
    }

    @Test
    void moveResourceSpecialDepot() {
    }

    @Test
    void buyDevelopmentCard() {
    }

    @Test
    void activateLeader() {
    }

    @Test
    void removeLeader() {
    }

    @Test
    void checkVaticanReport() {
    }

    @Test
    void notifyVaticanReport() {
    }

    @Test
    void sumVictoryPoints() {
    }

    @Test
    void moveFaithMarker() {
    }

    @Test
    void activateVaticanReport() {
    }
}