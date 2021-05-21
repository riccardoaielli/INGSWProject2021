package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;

/**
 * this class represents the characteristics of the development cards required for the activation of a leader card
 */
public class CardRequirement extends Object{
    private DevelopmentCardColor color;
    private Integer level;

    public CardRequirement(DevelopmentCardColor color, Integer level) {
        this.color = color;
        this.level = level;
    }

    public DevelopmentCardColor getColor() {
        return color;
    }

    public Integer getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object obj){
        CardRequirement cardRequirement = (CardRequirement) obj;
        return (color == cardRequirement.color) && (level.equals(cardRequirement.level));
    }
}
