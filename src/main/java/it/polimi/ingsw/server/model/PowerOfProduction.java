package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a power of production that players can use to produce resources and faith points from other resources
 */
public class PowerOfProduction {
    private Map<Resource,Integer> cost;
    private Map<Resource,Integer> production;

    /**
     * @param cost contains resources needed to activate the power
     * @param production contains resources produced after the production process
     */
    public PowerOfProduction(Map<Resource, Integer> cost, Map<Resource,Integer> production) throws InvalidParameterException {
        if(cost != null && production != null){
            this.cost = cost;
            this.production = production;
        }else
            throw new InvalidParameterException("Cost or production aren't defined");
    }

    /**
     * @return a copy of an Map of resources that represents the cost of the production
     */
    public Map<Resource, Integer> getCost() {
        return new HashMap<>(cost);
    }

    /**
     * @return a copy of  an Map of resources that represents the resources produced by the production process
     */
    public Map<Resource, Integer> getProduction() {
        return new HashMap<>(production);
    }

    /**
     * @param cost is the new cost associated at the production
     */
    public void setCost(Map<Resource, Integer> cost) throws InvalidParameterException{
        if (cost != null)
            this.cost = cost;
        else
            throw new InvalidParameterException("The cost isn't defined");
    }
    /**
     * @param production is the new HashMap of resources produced by the production process
     */
    public void setProduction(Map<Resource, Integer> production) throws InvalidParameterException{
        if (production != null)
            this.production = production;
        else
            throw new InvalidParameterException("The production isn't defined");
    }
}
