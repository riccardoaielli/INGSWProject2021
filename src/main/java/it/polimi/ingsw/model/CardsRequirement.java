package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.DevelopmentCardColor;

/**
 * this class represents the characteristics of the development cards required for the activation of a leader card
 */
public class CardsRequirement {
    private DevelopmentCardColor color;
    private Integer level;

    public CardsRequirement(DevelopmentCardColor color, Integer level) {
        this.color = color;
        this.level = level;
    }

    public DevelopmentCardColor getColor() {
        return color;
    }

    public Integer getLevel() {
        return level;
    }
}
