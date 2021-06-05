package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;

/**
 * This class represents the single player blue action token
 */
public class BlueToken extends SoloActionToken{

    CardGrid cardGrid;

    public BlueToken(CardGrid cardGrid){

        this.cardGrid = cardGrid;

    }

    /**
     * Implementation of soloAction method, it calls the remove method of cardGrid on the blue developmentCards
     */
    public void soloAction(){

        cardGrid.remove(DevelopmentCardColor.BLUE);
        cardGrid.remove(DevelopmentCardColor.BLUE);
        cardGrid.doNotify();

    }

}
