package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidRemovalException;

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
     * Checks that all the resources and their quantities in resourceMap are stored in Strongbox
     * @param resourceMap The resources and their quantities whose availability in Strongbox is verified
     * @throws InvalidRemovalException Exception thrown when not every resource in resourceMap is in Strongbox
     */
    public void checkAvailability(HashMap<Resource, Integer> resourceMap) throws InvalidRemovalException{
        //Checking if there are enough resources in strongbox to remove
        for (Resource resource : resourceMap.keySet())  {
            if (strongbox.get(resource) == null){
                throw new InvalidRemovalException();
            }
            else if (strongbox.get(resource) < resourceMap.get(resource)){
                throw new InvalidRemovalException();
            }
        }
    }

    /**
     * Removes the given map of resources from the strongbox, must be called only after performing checks with checkAvailability
     * @param resourceMap The map of resources to remove
     */

    public void uncheckedRemove(HashMap<Resource, Integer> resourceMap){
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
