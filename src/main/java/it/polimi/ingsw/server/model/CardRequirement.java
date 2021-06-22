package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;

/**
 * this class represents the characteristics of the development cards required for the activation of a leader card
 */
public class CardRequirement{
    private final DevelopmentCardColor color;
    private final Integer level;

    public CardRequirement(DevelopmentCardColor color, Integer level) {
        this.color = color;
        this.level = level;
    }

    /**
     * Getter for the color of the development card requirement
     * @return the color required to activate the card
     */
    public DevelopmentCardColor getColor() {
        return color;
    }

    /**
     * Getter for the level of the development card requirement
     * @return the level required to activate the card
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * Override of equals to check if two requirements are equal
     * @param obj is a cardRequirement
     * @return true if the two cardRequirements are equal
     */
    @Override
    public boolean equals(Object obj){
        CardRequirement cardRequirement = (CardRequirement) obj;
        return (color == cardRequirement.color) && (level.equals(cardRequirement.level));
    }
}
