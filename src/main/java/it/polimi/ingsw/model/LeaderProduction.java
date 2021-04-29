package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.NotEnoughWhiteMarblesException;

import java.util.HashMap;
import java.util.Map;

public class LeaderProduction extends LeaderCard{
    private HashMap<Resource, Integer> cost;

    public LeaderProduction(int victoryPoints, Requirement requirement, HashMap<Resource, Integer> cost) {
        super(victoryPoints, requirement);
        this.cost = cost;
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
    public HashMap<Resource, Integer> abilityProduction() {
        return cost;
    }
}
