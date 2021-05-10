package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;

import java.util.HashMap;

/**
 * This class represents a power of production that players can use to produce resources and faith points from other resources
 */
public class PowerOfProduction {
    private HashMap<Resource,Integer> cost;
    private HashMap<Resource,Integer> production;

    /**
     * @param cost contains resources needed to activate the power
     * @param production contains resources produced after the production process
     */
    public PowerOfProduction(HashMap<Resource, Integer> cost, HashMap<Resource,Integer> production) throws InvalidParameterException {
        if(cost != null && production != null){
            this.cost = cost;
            this.production = production;
        }else
            throw new InvalidParameterException();
    }


    /**
     * @return a copy of an HashMap of resources that represents the cost of the production
     */
    public HashMap<Resource, Integer> getCost() {
        return new HashMap<>(cost);
    }

    /**
     * @return a copy of  an HashMap of resources that represents the resources produced by the production process
     */
    public HashMap<Resource, Integer> getProduction() {
        return new HashMap<>(production);
    }

    /**
     * @param cost is the new cost associated at the production
     */
    public void setCost(HashMap<Resource, Integer> cost) throws InvalidParameterException{
        if (cost != null)
            this.cost = cost;
        else
            throw new InvalidParameterException();
    }
    /**
     * @param production is the new HashMap of resources produced by the production process
     */
    public void setProduction(HashMap<Resource, Integer> production) throws InvalidParameterException{
        if (production != null)
            this.production = production;
        else
            throw new InvalidParameterException();
    }
}