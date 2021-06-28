package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PersonalBoardTest {
    PersonalBoard personalBoard;
    Match match;

    @BeforeEach
    void setup(){
        try {
            match = new Match(1, 4,true);
        } catch (InvalidParameterException exception) {
            assert false;
        }
        try {
            match.addPlayer("Mario",new ViewStub());
            match.getPlayer("Mario").getPersonalBoard().setDemo();
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
        Map<Resource, Integer> resourceInWarehouse = new HashMap<>();
        Map<Resource, Integer> resourceInStrongbox = new HashMap<>();
        Map<Resource, Integer> costWarehouseDepot = new HashMap<>();
        Map<Resource, Integer> costStrongbox = new HashMap<>();

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

        personalBoard.endProduction();

        assertEquals(11, personalBoard.getStrongbox().getResourceQuantity(Resource.STONE));
        assertEquals(12, personalBoard.getStrongbox().getResourceQuantity(Resource.SERVANT));
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
        Map<Marble,Integer> testMarbles = new HashMap<>();
        try {
            personalBoard.takeFromMarket(0,0);
        } catch (InvalidParameterException e) {
            assert false;
        }
        testMarbles = personalBoard.getTemporaryMarbles();
        assertEquals(1,testMarbles.get(Marble.BLUEMARBLE));
        assertEquals(1,testMarbles.get(Marble.PURPLEMARBLE));
        assertEquals(1,testMarbles.get(Marble.REDMARBLE));
        assertEquals(1,testMarbles.get(Marble.WHITEMARBLE));

        try {
            personalBoard.takeFromMarket(1,1);
        } catch (InvalidParameterException e) {
            assert false;
        }
        testMarbles = personalBoard.getTemporaryMarbles();
        assertEquals(2,testMarbles.get(Marble.GREYMARBLE));
        assertEquals(1,testMarbles.get(Marble.PURPLEMARBLE));

        try {
            personalBoard.takeFromMarket(3,1);
        } catch (InvalidParameterException e) {
            assert true;
        }
    }

    @Test
    void transformWhiteMarble() {
    }

    @Test
    void transformMarbles() {
        Map<Marble, Integer> personalBoardTemporaryMarbles = personalBoard.getTemporaryMarbles();
        personalBoardTemporaryMarbles.put(Marble.BLUEMARBLE, 2);
        personalBoardTemporaryMarbles.put(Marble.REDMARBLE, 1);
        personalBoardTemporaryMarbles.put(Marble.WHITEMARBLE, 1);
        personalBoard.transformMarbles();
        Map<Resource, Integer> personalBoardTemporaryMapResource = personalBoard.getTemporaryMapResource();
        assertEquals(2, personalBoardTemporaryMapResource.get(Resource.SHIELD));
        assertNull(personalBoardTemporaryMapResource.get(Resource.FAITH));
    }

    @Test
    void addToWarehouseDepots() {
        Map<Resource, Integer> personalBoardTemporaryMapResource = personalBoard.getTemporaryMapResource();
        Map<Resource, Integer> singleMapResource1 = new HashMap<>();
        Map<Resource, Integer> singleMapResource2 = new HashMap<>();
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
        try {
            personalBoard.activateLeader(0);
        } catch (RequirementNotMetException e) {
            assert false;
        } catch (InvalidParameterException e) {
            assert true;
        }

        try {
            personalBoard.activateLeader(2);
        } catch (RequirementNotMetException e) {
            assert true;
        } catch (InvalidParameterException e) {
            assert false;
        }

        try {
            personalBoard.activateLeader(1);
        } catch (RequirementNotMetException | InvalidParameterException e) {
            assert false;
        }

        try {
            personalBoard.activateLeader(1);
        } catch (RequirementNotMetException e) {
            assert false;
        } catch (InvalidParameterException e) {
            assert true;
        }

    }

    @Test
    void removeLeader() {
    }

    @Test
    void sumVictoryPoints() {
    }

    @Test
    void vaticanReport() {
        PersonalBoard mario = null;
        PersonalBoard marco = null;
        PersonalBoard massimo = null;
        try {
            mario = match.getPlayer("Mario").getPersonalBoard();

            match.addPlayer("Marco",new ViewStub());
            marco = match.getPlayer("Marco").getPersonalBoard();
            marco.setDemo();

            match.addPlayer("Massimo",new ViewStub());
            massimo = match.getPlayer("Massimo").getPersonalBoard();
            massimo.setDemo();
        } catch (InvalidNickName invalidNickName) {
            assert false;
        }
        try {
            marco.moveFaithMarker(6);
            marco.checkVaticanReport();
            mario.moveFaithMarker(9);
            mario.checkVaticanReport();
        } catch (InvalidParameterException e) {
            assert false;
        }
        assertEquals(2,mario.getFaithTrack().getPopeFavourTileValue(1));
        assertEquals(2,marco.getFaithTrack().getPopeFavourTileValue(1));
        assertEquals(1,massimo.getFaithTrack().getPopeFavourTileValue(1));

        try {
            marco.moveFaithMarker(8);
            marco.checkVaticanReport();
            mario.moveFaithMarker(9);
            mario.checkVaticanReport();
        } catch (InvalidParameterException e) {
            assert false;
        }
        assertEquals(2,mario.getFaithTrack().getPopeFavourTileValue(1));
        assertEquals(2,marco.getFaithTrack().getPopeFavourTileValue(1));
        assertEquals(1,massimo.getFaithTrack().getPopeFavourTileValue(1));

        assertEquals(2,mario.getFaithTrack().getPopeFavourTileValue(2));
        assertEquals(2,marco.getFaithTrack().getPopeFavourTileValue(2));
        assertEquals(1,massimo.getFaithTrack().getPopeFavourTileValue(2));

        try {
            marco.moveFaithMarker(8);
            marco.checkVaticanReport();
            mario.moveFaithMarker(9);
            mario.checkVaticanReport();
        } catch (InvalidParameterException e) {
            assert false;
        }
        assertEquals(2,mario.getFaithTrack().getPopeFavourTileValue(3));
        assertEquals(2,marco.getFaithTrack().getPopeFavourTileValue(3));
        assertEquals(1,massimo.getFaithTrack().getPopeFavourTileValue(3));

    }
}