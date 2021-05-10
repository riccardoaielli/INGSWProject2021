package it.polimi.ingsw.server.model;


import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.PersonalBoard;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PersonalBoardTest {
    PersonalBoard personalBoard;
    Match match;

    @BeforeEach
    void setup(){
        try {
            match = new Match(1, 4);
        } catch (InvalidParameterException exception) {
            assert false;
        }
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
    void activateCardProduction() {
    }

    @Test
    void activateBasicProduction() {
        HashMap<Resource, Integer> resourceInWarehouse = new HashMap<>();
        HashMap<Resource, Integer> resourceInStrongbox = new HashMap<>();
        HashMap<Resource, Integer> costWarehouseDepot = new HashMap<>();
        HashMap<Resource, Integer> costStrongbox = new HashMap<>();

        resourceInWarehouse.put(Resource.COIN, 1);
        try {
            personalBoard.getWarehouseDepots().add(1, resourceInWarehouse);
        } catch (InvalidAdditionException e) {
            assert false;
        }

        resourceInWarehouse.clear();
        resourceInWarehouse.put(Resource.SHIELD, 2);

        try {
            personalBoard.getWarehouseDepots().add(2, resourceInWarehouse);
        } catch (InvalidAdditionException e) {
            assert false;
        }

        resourceInStrongbox.put(Resource.SERVANT, 3);
        personalBoard.getStrongbox().add(resourceInStrongbox);

        costStrongbox.put(Resource.SERVANT, 1);
        costWarehouseDepot.put(Resource.SHIELD, 1);

        try {
            personalBoard.activateBasicProduction(costStrongbox, costWarehouseDepot, Resource.STONE);
        } catch (InvalidProductionException | InvalidCostException | InvalidRemovalException e) {
            assert false;
        }

        assertEquals(1, personalBoard.getStrongbox().getResourceQuantity(Resource.STONE));
        assertEquals(2, personalBoard.getStrongbox().getResourceQuantity(Resource.SERVANT));
        assertEquals(1, personalBoard.getWarehouseDepots().getDepot(2).getNumberResources());


        try {
            personalBoard.activateBasicProduction(costStrongbox, costWarehouseDepot, Resource.COIN);
            assert false;
        } catch (InvalidProductionException e) {
            assert true;
        } catch (InvalidRemovalException | InvalidCostException e) {
            assert false;
        }
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