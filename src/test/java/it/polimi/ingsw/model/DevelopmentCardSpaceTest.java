package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.model.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardSpaceTest {

    /**
     * This test tests the addition of cards in different positions and with different levels
     */
    @Test
    void addCardTest() throws InvalidParameterException {
        int cardPosition = 1;
        int cardLevel = 1;
        PowerOfProduction powerOfProduction = new PowerOfProduction(new HashMap<>(), new HashMap<>());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardColor.GREEN,cardLevel, new HashMap<>(),5, powerOfProduction );
        DevelopmentCardSpace developmentCardSpace= new DevelopmentCardSpace();

        try {
            developmentCardSpace.addCard(card, cardPosition);
        }catch (Exception invalidDevelopmentCardException){
            fail("wrong exception thrown");
        }
        assertEquals(card, developmentCardSpace.getCard(cardPosition));
        cardLevel = 2;
        card = new DevelopmentCard(DevelopmentCardColor.GREEN, cardLevel, new HashMap<>(), 5, powerOfProduction);
        try {
            developmentCardSpace.addCard(card, cardPosition);
        }catch (Exception invalidDevelopmentCardException){
            fail("wrong exception thrown");
        }
            assertEquals(card, developmentCardSpace.getCard(cardPosition));

        try {
            developmentCardSpace.addCard(card, cardPosition);
        } catch (Exception invalidDevelopmentCardException){
            assert true;
        }

    }

    /**
     * This test checks different requirements in a development card space
     */
    @Test
    void checkRequirementTest() throws InvalidParameterException{
        int cardLevel = 1;
        DevelopmentCardColor color = DevelopmentCardColor.GREEN;
        PowerOfProduction powerOfProduction = new PowerOfProduction(new HashMap<>(), new HashMap<>());
        DevelopmentCard card = new DevelopmentCard(color,cardLevel, new HashMap<>(),5, powerOfProduction );
        DevelopmentCardSpace developmentCardSpace= new DevelopmentCardSpace();

        ArrayList<CardRequirement> requirements = new ArrayList<>();
        Integer levelRequirement = 0;
        CardRequirement singleRequirement = new CardRequirement(color,levelRequirement);
        requirements.add(singleRequirement);

        assertFalse(developmentCardSpace.checkRequirement(requirements));
        try {
            developmentCardSpace.addCard(card, 1);
        }catch (Exception invalidDevelopmentCardException){
            assert false;
        }

        assertTrue(developmentCardSpace.checkRequirement(requirements));
        levelRequirement = cardLevel;
        singleRequirement = new CardRequirement(color,levelRequirement);
        requirements.add(singleRequirement);
        assertTrue(developmentCardSpace.checkRequirement(requirements));
    }

    /**
     * This test tests if the the method to get the power of productions from a card space works properly
     */
    @Test
    void getPowerOfProductionTest() throws InvalidParameterException{
        int cardLevel1 = 1;
        int cardLevel2 = 2;
        PowerOfProduction powerOfProduction1 = new PowerOfProduction(new HashMap<>(), new HashMap<>());
        PowerOfProduction powerOfProduction2 = new PowerOfProduction(new HashMap<>(), new HashMap<>());

        DevelopmentCard card1 = new DevelopmentCard(DevelopmentCardColor.GREEN,cardLevel1, new HashMap<>(),5, powerOfProduction1 );
        DevelopmentCard card2 = new DevelopmentCard(DevelopmentCardColor.GREEN,cardLevel2, new HashMap<>(),5, powerOfProduction2 );
        DevelopmentCardSpace developmentCardSpace= new DevelopmentCardSpace();

        try {
            developmentCardSpace.addCard(card1, 1);
            developmentCardSpace.addCard(card1, 2);
            developmentCardSpace.addCard(card2, 1);
        }catch (Exception invalidDevelopmentCardException){
            assert false;
        }

        assertEquals(powerOfProduction1, developmentCardSpace.getPowerOfProduction().get(1));
        assertEquals(powerOfProduction2, developmentCardSpace.getPowerOfProduction().get(0));
    }

    /**
     * this test tests the calculation of victory points from a development card space
     */
    @Test
    void getVictoryPointsTest() throws InvalidParameterException{
        int victoryPoints1 = 10;
        int victoryPoints2 = 12;
        int cardLevel = 1;
        PowerOfProduction powerOfProduction = new PowerOfProduction(new HashMap<>(), new HashMap<>());

        DevelopmentCard card1 = new DevelopmentCard(DevelopmentCardColor.GREEN,cardLevel, new HashMap<>(),victoryPoints1, powerOfProduction );
        DevelopmentCard card2 = new DevelopmentCard(DevelopmentCardColor.GREEN,cardLevel, new HashMap<>(),victoryPoints2, powerOfProduction );
        DevelopmentCardSpace developmentCardSpace= new DevelopmentCardSpace();

        try {
            developmentCardSpace.addCard(card1, 1);
            developmentCardSpace.addCard(card2, 2);
        }catch (Exception invalidDevelopmentCardException){
            assert false;
        }

        assertEquals(victoryPoints1 + victoryPoints2, developmentCardSpace.getVictoryPoints());
    }

}