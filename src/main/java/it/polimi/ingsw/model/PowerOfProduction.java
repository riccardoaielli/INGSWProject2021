package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class PowerOfProduction {
    HashMap<Resource,Integer> cost;
    HashMap<Resource,Integer> production;

    /**
     * @param cost contains resources needed to activate the power
     * @param production contains resources produced after the production process
     */
    public PowerOfProduction(HashMap<Resource, Integer> cost, HashMap<Resource,Integer> production) {
        assert cost != null && production != null;
        this.cost = cost;
        this.production = production;
    }

    /**
     * @return an HashMap of resources that represents the cost of the production
     */
    public HashMap<Resource, Integer> getCost() {
        return cost;
    }

    /**
     * @return an HashMap of resources that represents the resources produced by the production process
     */
    public HashMap<Resource, Integer> getProduction() {
        return production;
    }

    /**
     * @param cost is the new cost associated at the production
     */
    public void setCost(HashMap<Resource, Integer> cost) {
        assert cost != null;
        this.cost = cost;
    }
    /**
     * @param production is the new HashMap of resources produced by the production process
     */
    public void setProduction(HashMap<Resource, Integer> production) {
        assert cost != null;
        this.production = production;
    }
}
