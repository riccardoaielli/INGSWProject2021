package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NotEnoughResourcesException;

import java.util.HashMap;

public class Strongbox {
    private HashMap<Resource, Integer> strongbox;

    /**
     * Constructor that initialize an empty HashMap
     */
    public Strongbox() {
        strongbox = new HashMap<>();
    }

    /**
     * Stores the given map of resources in the strongbox
     * @param resourceMap The map of resources to add
     */
    public void add(HashMap<Resource, Integer> resourceMap) {
        resourceMap.forEach((resource, quantity) -> strongbox.merge(resource, quantity, Integer::sum));
    }

    /**
     * Removes the given map of resources from the strongbox
     * @param resourceMap The map of resources to remove
     * @throws NotEnoughResourcesException When trying to remove more resources than there are in strongbox
     */

    public void remove(HashMap<Resource, Integer> resourceMap) throws NotEnoughResourcesException {
        for (Resource resource : resourceMap.keySet())  {
            if (strongbox.get(resource) == null){
                throw new NotEnoughResourcesException();
            }
            else if (strongbox.get(resource) < resourceMap.get(resource)){
                throw new NotEnoughResourcesException();
            }
        }

        for (Resource resource : resourceMap.keySet())  {
            if (strongbox.get(resource).equals(resourceMap.get(resource)))
                strongbox.remove(resource);
            else
                strongbox.merge(resource, -resourceMap.get(resource), Integer::sum);
        }

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
