package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;

/**
 * This class represents the single player purple action token
 */
public class PurpleToken extends SoloActionToken{
    CardGrid cardGrid;

    public PurpleToken(CardGrid cardGrid){
        this.cardGrid = cardGrid;
    }

    /**
     * Implementation of soloAction method, it calls the remove method of cardGrid on the purple developmentCards
     */
    public void soloAction(){
        cardGrid.remove(DevelopmentCardColor.PURPLE);
        cardGrid.remove(DevelopmentCardColor.PURPLE);
        cardGrid.doNotify();
    }
}
