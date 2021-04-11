package it.polimi.ingsw.model;

import java.util.HashMap;

public class Strongbox implements Manageable{
    private HashMap<Resource, Integer> strongbox;

    /**
     * Constructor that initialize an empty HashMap
     */
    public Strongbox() {
        strongbox = new HashMap<>();
    }

    /**
     * Stores a given quantity of the passed resource
     * @param resource The resource to be added, must not be faith
     * @param quantity  The quantity of the resource to be added, must be a positive number
     */
    @Override
    public void add(Resource resource, int quantity) {
        strongbox.merge(resource, quantity, Integer::sum);
    }

    /**
     * Removes a given quantity of the passed resource
     * If the passed quantity is greater than the  quantity of the resource in strongbox, the quantity is set to 0
     * @param resource The resource to be removed, must exist in Strongbox and must not be Faith and must be previously added
     * @param quantity The quantity of the resource to be removed, must be a positive number smaller
     *                 than the quantity available
     */
    @Override
    public void remove(Resource resource, int quantity) {
        if (strongbox.get(resource) == quantity)
            strongbox.remove(resource);
        else
            strongbox.merge(resource, -quantity, Integer::sum);
    }

    /**
     * Method to get the quantity of a given resource in strongbox
     * @param resource The resource whose quantity is requested
     * @return The quantity of the specified resource if present, else 0
     */
    public int getResourceQuantity(Resource resource) {
        return strongbox.getOrDefault(resource, 0);
    }

    /**
     * Method to calculate the sum of all resources in the strongbox
     * @return The sum of all resources in the strongbox
     */
    public int getTotalResources(){
        int total = 0;
        //Iterating in the Collection of values of the HashMap
        for (int singleQuantity : strongbox.values()){
            total += singleQuantity;
        }
        return total;
    }
}
