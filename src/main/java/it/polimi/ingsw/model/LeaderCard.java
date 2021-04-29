package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.NotEnoughWhiteMarblesException;

import java.util.HashMap;

/**
 * this class represent the abstract leader card
 */
public abstract class LeaderCard {
    private int victoryPoints;
    private Requirement requirement;
    private Boolean active;

    public LeaderCard(int victoryPoints, Requirement requirement) {
        this.victoryPoints = victoryPoints;
        this.requirement = requirement;
        this.active = false;
    }

    public void activate(){
        active = true;
    }

    public Boolean isActive(){
        return active;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public abstract void abilityDiscount(HashMap<Resource,Integer> resources);

    public abstract void abilityDepot();

    public abstract void abilityMarble(HashMap<Marble,Integer> temporaryMapMarble, int numOfTransformation) throws NotEnoughWhiteMarblesException;

    public abstract PowerOfProduction abilityProduction();

}
