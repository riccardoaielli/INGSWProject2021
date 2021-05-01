package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.NotEnoughWhiteMarblesException;

import java.util.HashMap;

public class LeaderProduction extends LeaderCard{
    private PowerOfProduction powerOfProduction;

    public LeaderProduction(int victoryPoints, Requirement requirement, PowerOfProduction powerOfProduction) {
        super(victoryPoints, requirement);
        this.powerOfProduction = powerOfProduction;
    }

    /**
     * @return the power of production of the leader card
     */
    @Override
    public PowerOfProduction abilityProduction() {
        return powerOfProduction;
    }
}
