package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.server.model.exceptions.InvalidNickName;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoloMatchTest {

    @Test
    void soloMatchTest(){
        SoloMatch match = null;
        try {
            match = new SoloMatch(1,false);
            match.addPlayer("Pippo",new ViewStub());
        } catch (InvalidParameterException | InvalidNickName e) {
            assert false;
        }
        int numOfCards;
        CardGrid cardGrid = match.getCardGrid();

        BlueToken blueToken = new BlueToken(match.getCardGrid());
        blueToken.soloAction();
        numOfCards = cardGrid.getNumOfCardsByColor(DevelopmentCardColor.BLUE);
        assertEquals(10,numOfCards);
        GreenToken greenToken = new GreenToken(match.getCardGrid());
        greenToken.soloAction();
        numOfCards = cardGrid.getNumOfCardsByColor(DevelopmentCardColor.GREEN);
        assertEquals(10,numOfCards);
        YellowToken yellowToken = new YellowToken(match.getCardGrid());
        yellowToken.soloAction();
        numOfCards = cardGrid.getNumOfCardsByColor(DevelopmentCardColor.YELLOW);
        assertEquals(10,numOfCards);
        PurpleToken purpleToken = new PurpleToken(match.getCardGrid());
        purpleToken.soloAction();
        numOfCards = cardGrid.getNumOfCardsByColor(DevelopmentCardColor.PURPLE);
        assertEquals(10,numOfCards);

        BlackCrossOne blackCrossOne = new BlackCrossOne(match.getLorenzo());
        blackCrossOne.soloAction();
        assertEquals(1,match.getLorenzo().getFaithTrackPositionBlack());
        BlackCrossTwo blackCrossTwo = new BlackCrossTwo(match.getLorenzo());
        blackCrossTwo.soloAction();
        assertEquals(3,match.getLorenzo().getFaithTrackPositionBlack());
        match.moveFaithMarkerAll(5);
        assertEquals(8,match.getLorenzo().getFaithTrackPositionBlack());

        SoloActionToken token = new SoloActionToken() {
        };
        token.soloAction();
        match.addPlayerReady();
        match.nextPlayer();
        blackCrossOne.soloAction();
        try {
            match.getPlayer("Pippo").getPersonalBoard().moveFaithMarker(30);
        } catch (InvalidNickName invalidNickName) {
            assert false;
        }

    }

}