package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.DevelopmentCardColor;

/**
 * This class represents the single player green action token
 */
public class GreenToken extends SoloActionToken{

    CardGrid cardGrid;

    public GreenToken(CardGrid cardGrid){

        this.cardGrid = cardGrid;

    }

    /**
     * Implementation of soloAction method, it calls the remove method of cardGrid on the green developmentCards
     */
    public void soloAction(){

        cardGrid.remove(DevelopmentCardColor.GREEN);
        cardGrid.remove(DevelopmentCardColor.GREEN);

    }

}
