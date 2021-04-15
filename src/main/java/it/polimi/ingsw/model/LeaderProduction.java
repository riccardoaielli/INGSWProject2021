package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NotEnoughWhiteMarblesException;

import java.util.HashMap;

public class LeaderProduction extends LeaderCard{
    private PowerOfProduction powerOfProduction;

    public LeaderProduction(int victoryPoints, Requirement requirement, PowerOfProduction powerOfProduction) {
        super(victoryPoints, requirement);
        this.powerOfProduction = powerOfProduction;
    }

    @Override
    public void abilityDiscount(HashMap<Resource, Integer> resources) {

    }

    @Override
    public void abilityDepot() {

    }

    @Override
    public void abilityMarble(HashMap<Marble, Integer> temporaryMapMarble, int numOfTransformation) throws NotEnoughWhiteMarblesException {

    }

    /**
     * @return the power of production of the leader card
     */
    @Override
    public PowerOfProduction abilityProduction() {
        return powerOfProduction;
    }
}
