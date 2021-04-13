package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.DevelopmentCardColor;

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
