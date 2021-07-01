package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.PersonalBoardPhase;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        DevelopmentCard card = null;
        Map<Resource,Integer> costStrongbox = new HashMap<>();
        costStrongbox.put(Resource.SERVANT,1);
        Map<Resource,Integer> costWarehouseDepot = new HashMap<>();
        costWarehouseDepot.put(Resource.STONE,1);
        try {
            card = personalBoard.getCardGrid().getCard(2,0);
            personalBoard.getDevelopmentCardSpace().addCard(card,1);
            personalBoard.getWarehouseDepots().add(2,costWarehouseDepot);
            personalBoard.getWarehouseDepots().add(2,costWarehouseDepot);
        } catch (NoCardException | InvalidDevelopmentCardException | InvalidParameterException | InvalidAdditionException e) {
            assert false;
        }

        try {
            personalBoard.activateCardProduction(costStrongbox,costWarehouseDepot,1);
        } catch (InvalidProductionException | InvalidRemovalException | InvalidCostException | InvalidParameterException e) {
            assert false;
        }
        try {
            personalBoard.activateCardProduction(costStrongbox,costWarehouseDepot,1);
        } catch (InvalidRemovalException | InvalidCostException | InvalidParameterException e) {
            assert false;
        } catch (InvalidProductionException e) {
            assert true;
        }

        personalBoard.endProduction();

        assertEquals(12,personalBoard.getStrongbox().getResourceQuantity(Resource.COIN));
        assertEquals(1,personalBoard.getFaithTrack().getFaithTrackPosition());
        assertEquals(1,personalBoard.getWarehouseDepots().getDepot(2).getMapResource().get(Resource.STONE));

        assertEquals(PersonalBoardPhase.MAIN_TURN_ACTION_DONE,personalBoard.getPersonalBoardPhase());
        personalBoard.endTurn();
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
        Map<Resource,Integer> costStrongbox = new HashMap<>();
        costStrongbox.put(Resource.STONE,1);
        Map<Resource,Integer> costWarehouseDepot = new HashMap<>();
        try {
            personalBoard.getDevelopmentCardSpace().addCard(match.getCardGrid().getCard(2,3),1);
            personalBoard.getDevelopmentCardSpace().addCard(match.getCardGrid().getCard(1,3),1);
            personalBoard.discardInitialLeader(1,2);
        } catch ( InvalidParameterException | NoCardException | InvalidDevelopmentCardException e) {
            assert false;
        }
        try {
            personalBoard.activateLeaderProduction(costStrongbox,costWarehouseDepot,2,Resource.COIN);
        } catch ( InvalidCostException | InvalidLeaderAction | InvalidRemovalException e) {
            assert false;
        } catch (InvalidProductionException e) {
            assert true;
        }

        try {
            personalBoard.activateLeader(2);
        } catch (RequirementNotMetException | InvalidParameterException e) {
            assert false;
        }


        try {
            personalBoard.activateLeaderProduction(costStrongbox,costWarehouseDepot,5,Resource.COIN);
        } catch (InvalidCostException | InvalidLeaderAction | InvalidRemovalException e) {
            assert false;
        } catch (InvalidProductionException e) {
            assert true;
        }

        try {
            personalBoard.activateLeaderProduction(costStrongbox,costWarehouseDepot,2,Resource.FAITH);
        } catch (InvalidCostException | InvalidLeaderAction | InvalidRemovalException e) {
            assert false;
        } catch (InvalidProductionException e) {
            assert true;
        }

        try {
            personalBoard.activateLeaderProduction(costStrongbox,costWarehouseDepot,2,Resource.COIN);
        } catch (InvalidProductionException | InvalidCostException | InvalidLeaderAction | InvalidRemovalException e) {
            assert false;
        }

        try {
            personalBoard.activateLeaderProduction(costStrongbox,costWarehouseDepot,2,Resource.COIN);
        } catch (InvalidCostException | InvalidLeaderAction | InvalidRemovalException e) {
            assert false;
        } catch (InvalidProductionException e) {
            assert true;
        }

        assertEquals(1,personalBoard.getFaithTrack().getFaithTrackPosition());
        assertEquals(11,personalBoard.getStrongbox().getResourceQuantity(Resource.COIN));
        assertEquals(9,personalBoard.getStrongbox().getResourceQuantity(Resource.STONE));

    }

    @Test
    void takeFromMarket() {
        Map<Marble,Integer> testMarbles = new HashMap<>();
        Map<Resource,Integer> testTemporaryResources = new HashMap<>();
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

        personalBoard.transformMarbles();
        testTemporaryResources = personalBoard.getTemporaryMapResource();
        assertEquals(1,testTemporaryResources.get(Resource.SHIELD));
        assertEquals(1,testTemporaryResources.get(Resource.SERVANT));
        assertEquals(1,personalBoard.getFaithTrack().getFaithTrackPosition());

        personalBoard.discardResourcesFromMarket();
        assertEquals(0,testTemporaryResources.size());

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
    void transformWhiteMarble(){
        CardGrid cardGrid = match.getCardGrid();
        try {
            personalBoard.getDevelopmentCardSpace().addCard(cardGrid.getCard(2,3),1);
            personalBoard.getDevelopmentCardSpace().addCard(cardGrid.getCard(1,3),1);
            personalBoard.getDevelopmentCardSpace().addCard(cardGrid.getCard(2,0),2);
            personalBoard.activateLeader(3);
            personalBoard.activateLeader(1);
            personalBoard.takeFromMarket(0,0);
        } catch (InvalidDevelopmentCardException | InvalidParameterException | NoCardException | RequirementNotMetException e) {
            assert false;
        }
        Map<Marble, Integer> temporaryMarbles = new HashMap<>(personalBoard.getTemporaryMarbles());

        try {
            personalBoard.transformWhiteMarble(3,2);
        } catch (InvalidParameterException | InvalidLeaderAction e) {
            assert false;
        } catch (NotEnoughWhiteMarblesException e) {
            assert true;
        }
        try {
            personalBoard.transformWhiteMarble(1,1);
        } catch (InvalidParameterException | NotEnoughWhiteMarblesException e) {
            assert false;
        } catch (InvalidLeaderAction invalidLeaderAction) {
            assert true;
        }

        temporaryMarbles.put(Marble.YELLOWMARBLE,1);
        temporaryMarbles.remove(Marble.WHITEMARBLE,1);
        temporaryMarbles.putIfAbsent(Marble.WHITEMARBLE,0);
        try {
            personalBoard.transformWhiteMarble(10,1);
        } catch (InvalidLeaderAction | NotEnoughWhiteMarblesException e) {
            assert false;
        } catch (InvalidParameterException invalidLeaderAction) {
            assert true;
        }
        try {
            personalBoard.transformWhiteMarble(3,1);
        } catch (InvalidParameterException | NotEnoughWhiteMarblesException | InvalidLeaderAction e) {
            assert false;
        }
        assertEquals(temporaryMarbles,personalBoard.getTemporaryMarbles());

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
        Map<Resource, Integer> firstDepot = new HashMap<>();
        firstDepot.put(Resource.COIN,1);
        Map<Resource, Integer> secondDepot = new HashMap<>();
        secondDepot.put(Resource.SHIELD,2);
        try {
            personalBoard.getWarehouseDepots().add(1,firstDepot);
            personalBoard.getWarehouseDepots().add(2,secondDepot);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        try {
            personalBoard.swapResourceStandardDepot(1,2);
        } catch (InvalidSwapException e) {
            assert true;
        }
        Map<Resource, Integer> thirdDepot = new HashMap<>();
        thirdDepot.put(Resource.SERVANT,1);
        try {
            personalBoard.getWarehouseDepots().add(3,thirdDepot);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        try {
            personalBoard.swapResourceStandardDepot(2,3);
        } catch (InvalidSwapException e) {
            assert false;
        }
        assertEquals(thirdDepot,personalBoard.getWarehouseDepots().getDepot(2).getMapResource());
        assertEquals(secondDepot,personalBoard.getWarehouseDepots().getDepot(3).getMapResource());
    }

    @Test
    void moveResourceSpecialDepot() {
        Map<Resource, Integer> firstDepot = new HashMap<>();
        firstDepot.put(Resource.COIN,1);
        Map<Resource, Integer> secondDepot = new HashMap<>();
        secondDepot.put(Resource.SERVANT,2);
        try {
            personalBoard.getWarehouseDepots().add(1,firstDepot);
            personalBoard.getWarehouseDepots().add(2,secondDepot);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        try {
            personalBoard.moveResourceSpecialDepot(1,4,1);
        } catch (InvalidAdditionException | InvalidRemovalException e) {
            assert false;
        } catch (InvalidMoveException e) {
            assert true;
        }
        personalBoard.getWarehouseDepots().addSpecialDepot(Resource.SERVANT);
        personalBoard.getWarehouseDepots().addSpecialDepot(Resource.STONE);
        Map<Resource, Integer> fourthDepot = new HashMap<>();
        fourthDepot.put(Resource.SERVANT,1);
        try {
            personalBoard.getWarehouseDepots().add(4,fourthDepot);
        } catch (InvalidAdditionException e) {
            assert false;
        }

        try {
            personalBoard.moveResourceSpecialDepot(1,4,2);
        } catch (InvalidAdditionException | InvalidMoveException e) {
            assert false;
        } catch (InvalidRemovalException e) {
            assert true;
        }

        try {
            personalBoard.moveResourceSpecialDepot(2,4,2);
        } catch (InvalidAdditionException e) {
            assert true;
        } catch (InvalidRemovalException | InvalidMoveException e) {
            assert false;
        }

        try {
            personalBoard.moveResourceSpecialDepot(2,4,1);
        } catch (InvalidAdditionException | InvalidMoveException | InvalidRemovalException e) {
            assert false;
        }

        assertEquals(fourthDepot,personalBoard.getWarehouseDepots().getDepot(2).getMapResource());
        assertEquals(secondDepot,personalBoard.getWarehouseDepots().getDepot(4).getMapResource());

    }

    @Test
    void buyDevelopmentCard() {
        DevelopmentCard card = null;
        Map<Resource, Integer> costStrongbox = new HashMap<>();
        costStrongbox.put(Resource.SERVANT,2);
        Map<Resource, Integer> costWarehouseDepot = new HashMap<>();
        costWarehouseDepot.put(Resource.SHIELD,3);
        try {
            card = match.getCardGrid().getCard(2,0);
        } catch (NoCardException e) {
            assert false;
        }
        try {
            personalBoard.buyDevelopmentCard(3,1,costStrongbox,costWarehouseDepot,0,1);
        } catch (NoCardException | InvalidLeaderAction | InvalidRemovalException | InvalidDevelopmentCardException | InvalidParameterException e) {
            assert false;
        } catch (InvalidCostException e) {
            assert true;
        }
        try {
            personalBoard.buyDevelopmentCard(3,1,costStrongbox,costWarehouseDepot,6,1);
        } catch (NoCardException | InvalidCostException | InvalidRemovalException | InvalidDevelopmentCardException | InvalidParameterException e) {
            assert false;
        } catch (InvalidLeaderAction e) {
            assert true;
        }

        costStrongbox = new HashMap<>();
        costStrongbox.put(Resource.COIN,1);
        costWarehouseDepot = new HashMap<>();
        Map<Resource, Integer> resourceToDepot = new HashMap<>();
        resourceToDepot.put(Resource.COIN,1);
        costWarehouseDepot.put(Resource.COIN,1);
        try {
            personalBoard.getWarehouseDepots().add(1,resourceToDepot);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        resourceToDepot = new HashMap<>();
        resourceToDepot.put(Resource.SHIELD,2);
        costWarehouseDepot.put(Resource.SHIELD,2);
        try {
            personalBoard.buyDevelopmentCard(3,1,costStrongbox,costWarehouseDepot,0,1);
        } catch (NoCardException | InvalidCostException| InvalidLeaderAction | InvalidDevelopmentCardException | InvalidParameterException e) {
            assert false;
        } catch (InvalidRemovalException e) {
            assert true;
        }
        try {
            personalBoard.getWarehouseDepots().add(3,resourceToDepot);
        } catch (InvalidAdditionException e) {
            assert false;
        }
        try {
            personalBoard.buyDevelopmentCard(3,1,costStrongbox,costWarehouseDepot,1,1);
        } catch (NoCardException | InvalidRemovalException | InvalidDevelopmentCardException | InvalidParameterException | InvalidCostException e) {
            assert false;
        } catch (InvalidLeaderAction e) {
            assert true;
        }
        try {
            personalBoard.buyDevelopmentCard(3,1,costStrongbox,costWarehouseDepot,0,1);
        } catch (NoCardException | InvalidRemovalException | InvalidDevelopmentCardException | InvalidParameterException | InvalidCostException | InvalidLeaderAction e) {
            assert false;
        }
        try {
            assertNotEquals(card, match.getCardGrid().getCard(2,0));
        } catch (NoCardException e) {
            assert false;
        }
        assertEquals(card,personalBoard.getDevelopmentCardSpace().getCard(1));
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

        Map<Resource,Integer> map = new HashMap<>();
        map.put(Resource.SHIELD,10);
        personalBoard.getStrongbox().uncheckedRemove(map);
        try {
            personalBoard.activateLeader(1);
        } catch (RequirementNotMetException e) {
            assert true;
        } catch (InvalidParameterException e) {
            assert false;
        }

        map = new HashMap<>();
        map.put(Resource.SHIELD,5);
        personalBoard.getStrongbox().add(map);
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
        ArrayList<LeaderCard> leaderCards = personalBoard.getLeaderCards();
        assertEquals(52,leaderCards.get(0).getId());
        assertEquals(56,leaderCards.get(1).getId());
        assertEquals(60,leaderCards.get(2).getId());
        assertEquals(64,leaderCards.get(3).getId());

        try {
            personalBoard.activateLeader(1);
        } catch (RequirementNotMetException | InvalidParameterException e) {
            assert false;
        }
        try {
            personalBoard.removeLeader(1);
        } catch (InvalidParameterException e) {
            assert true;
        }
        try {
            personalBoard.removeLeader(0);
        } catch (InvalidParameterException e) {
            assert true;
        }

        try {
            personalBoard.removeLeader(5);
        } catch (InvalidParameterException e) {
            assert true;
        }

        try {
            personalBoard.removeLeader(3);
        } catch (InvalidParameterException e) {
            assert false;
        }
        assertEquals(52,leaderCards.get(0).getId());
        assertEquals(56,leaderCards.get(1).getId());
        assertEquals(64,leaderCards.get(2).getId());
        assertEquals(3,leaderCards.size());

        try {
            personalBoard.removeLeader(2);
            personalBoard.removeLeader(2);
        } catch (InvalidParameterException e) {
            assert false;
        }
        assertEquals(1,leaderCards.size());

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

        marco.moveFaithMarker(6);
        marco.checkVaticanReport();
        mario.moveFaithMarker(9);
        mario.checkVaticanReport();

        assertEquals(2,mario.getFaithTrack().getPopeFavourTileValue(1));
        assertEquals(2,marco.getFaithTrack().getPopeFavourTileValue(1));
        assertEquals(1,massimo.getFaithTrack().getPopeFavourTileValue(1));


        marco.moveFaithMarker(8);
        marco.checkVaticanReport();
        mario.moveFaithMarker(9);
        mario.checkVaticanReport();

        assertEquals(2,mario.getFaithTrack().getPopeFavourTileValue(1));
        assertEquals(2,marco.getFaithTrack().getPopeFavourTileValue(1));
        assertEquals(1,massimo.getFaithTrack().getPopeFavourTileValue(1));

        assertEquals(2,mario.getFaithTrack().getPopeFavourTileValue(2));
        assertEquals(2,marco.getFaithTrack().getPopeFavourTileValue(2));
        assertEquals(1,massimo.getFaithTrack().getPopeFavourTileValue(2));

        marco.moveFaithMarker(8);
        marco.checkVaticanReport();
        mario.moveFaithMarker(9);
        mario.checkVaticanReport();

        assertEquals(2,mario.getFaithTrack().getPopeFavourTileValue(3));
        assertEquals(2,marco.getFaithTrack().getPopeFavourTileValue(3));
        assertEquals(1,massimo.getFaithTrack().getPopeFavourTileValue(3));

    }
}