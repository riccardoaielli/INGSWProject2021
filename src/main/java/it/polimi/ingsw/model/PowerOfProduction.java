package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class PowerOfProduction {
    HashMap<Resource,Integer> cost;
    HashMap<Resource,Integer> production;

    public PowerOfProduction(HashMap<Resource, Integer> cost, HashMap<Resource,Integer> production) {
        assert cost != null && production != null;
        this.cost = cost;
        this.production = production;
    }

    public Map<Resource, Integer> getCost() {
        return cost;
    }

    public Map<Resource, Integer> getProduction() {
        return production;
    }

    public void setCost(HashMap<Resource, Integer> cost) {
        assert cost != null;
        this.cost = cost;
    }

    public void setProduction(HashMap<Resource, Integer> production) {
        assert cost != null;
        this.production = production;
    }
}
