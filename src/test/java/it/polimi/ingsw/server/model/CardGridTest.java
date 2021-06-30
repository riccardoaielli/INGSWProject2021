package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.CardGrid;
import it.polimi.ingsw.server.model.DevelopmentCard;
import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.server.model.exceptions.NoCardException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardGridTest {

    CardGrid cardGrid = new CardGrid(false);

    @Test
    public void getCardTest(){

        DevelopmentCard card0 = null;
        try {
            card0 = cardGrid.getCard(0, 0);
        } catch (NoCardException e) {
            e.printStackTrace();
        }
        assertEquals(card0.getLevel(), 3);
        assertEquals(card0.getColor(), DevelopmentCardColor.GREEN);

        DevelopmentCard card00 = null;
        try {
            card00 = cardGrid.getCard(0, 0);
        } catch (NoCardException e) {
            e.printStackTrace();
        }
        assertEquals(card0, card00);

        DevelopmentCard card1 = null;
        try {
            card1 = cardGrid.getCard(1, 0);
        } catch (NoCardException e) {
            e.printStackTrace();
        }
        assertEquals(card1.getLevel(), 2);

        DevelopmentCard card2 = null;
        try {
            card2 = cardGrid.getCard(2, 0);
        } catch (NoCardException e) {
            e.printStackTrace();
        }
        assertEquals(card2.getLevel(), 1);

    }

    @Test
    public void buyCardTest(){

        DevelopmentCard card0 = null;

        try {
            card0 = cardGrid.buyCard(2, 0);
        } catch (NoCardException e) {
            assert false;
        }

        DevelopmentCard card00 = null;
        try {
            card00 = cardGrid.buyCard(2, 0);
        } catch (NoCardException e) {
            assert false;
        }
        assertNotEquals(card0, card00);

        try {
            card0 = cardGrid.buyCard(1, 1);
            card0 = cardGrid.buyCard(1, 1);
            card0 = cardGrid.buyCard(1, 1);
            card0 = cardGrid.buyCard(1, 1);
            card0 = cardGrid.buyCard(1, 1);
        } catch (NoCardException e) {
            assert true;
        }
    }

    @Test
    public void removeTest(){

        for(int i=0; i<4; i++)
            cardGrid.remove(DevelopmentCardColor.BLUE);

        assertThrows(NoCardException.class, ()-> cardGrid.getCard(2, 1));

        assertEquals("Non hai ancora perso", cardGrid.remove(DevelopmentCardColor.BLUE));

        for(int i=0; i<6; i++)
            cardGrid.remove(DevelopmentCardColor.BLUE);

        //DevelopmentCard card0 = cardGrid.getCard(0, 0);
        //assertEquals(false, cardGrid.(DevelopmentCardColor.BLUE);

        assertEquals("Hai Perso", cardGrid.remove(DevelopmentCardColor.BLUE));

        for(int i=0; i<8; i++)
            cardGrid.remove(DevelopmentCardColor.BLUE);

        assertEquals("Hai gia perso", cardGrid.remove(DevelopmentCardColor.BLUE));

        for(int i=0; i<20; i++)
            cardGrid.remove(DevelopmentCardColor.GREEN);
        assertEquals("Hai gia perso", cardGrid.remove(DevelopmentCardColor.GREEN));

    }

}