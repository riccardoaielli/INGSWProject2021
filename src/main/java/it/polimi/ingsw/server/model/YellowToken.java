package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;

/**
 * This class represents the single player yellow action token
 */
public class YellowToken extends SoloActionToken{

    CardGrid cardGrid;

    public YellowToken(CardGrid cardGrid){

        this.cardGrid = cardGrid;

    }

    /**
     * Implementation of soloAction method, it calls the remove method of cardGrid on the yellow developmentCards
     */
    public void soloAction(){

        cardGrid.remove(DevelopmentCardColor.YELLOW);
        cardGrid.remove(DevelopmentCardColor.YELLOW);

    }

}
