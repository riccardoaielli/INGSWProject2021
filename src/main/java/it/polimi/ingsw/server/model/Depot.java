package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidAdditionException;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents one of the three depots of the warehouse depots
 */
public class Depot {
    private final int SIZE;

    private Map<Resource, Integer> mapResource;

    public Depot(int size) {
        SIZE = size;
        mapResource = new HashMap<>();
    }

    /**
     * Getter for the size of the depot
     * @return the amount of resources that can be stored in the depot
     */
    public int getSIZE() {
        return SIZE;
    }

    /**
     * Getter for the resource stored in the depot
     * @return a map with the resource stored in the depot
     */
    public Map<Resource, Integer> getMapResource() {
        return mapResource;
    }
    public void setMapResource(Map<Resource, Integer> mapResource) {
        this.mapResource = mapResource;
    }

    /**
     * Method to check the presence in the depot of the passed resource
     * @param resource The resource to be checked
     * @return true if the resource is in the depot, false otherwise
     */
    public Boolean checkResource(Resource resource){
        return mapResource.get(resource) != null;
    }

    /**
     * Checks if a resource map can be added to the depot
     * @param addedResourceMap The resource map to be added, must be a map with a single key
     * @throws InvalidAdditionException When there is not enough space or there is already another resource in the depot
     */
    public void checkAdd(Map<Resource, Integer> addedResourceMap) throws InvalidAdditionException{
        Resource resource = addedResourceMap.keySet().iterator().next();
        //Making sure there isn't another resource in the depot
        if (!(this.checkResource(resource) || mapResource.isEmpty())) throw new InvalidAdditionException("There is another resource in the depot");
        //Making sure the maximum size of depot is not exceeded
        if (!(mapResource.getOrDefault(resource, 0) + addedResourceMap.get(resource) <= SIZE)) throw new InvalidAdditionException("Not enough space");
    }

    /**
     * Adds a resource to the depot, must be called only after performing checks with checkAdd
     * @param addedResourceMap resource map to add, must contain a single key
     */
    public void uncheckedAdd(Map<Resource, Integer> addedResourceMap){
        Resource resource = addedResourceMap.keySet().iterator().next();
        mapResource.merge(resource, addedResourceMap.get(resource), Integer::sum);
    }

    /**
     * This method adds the passed single resource map to the map of the depot
     * @param addedResourceMap The single resource map to be added, must be a map with a single key
     * @throws InvalidAdditionException When there is not enough space or there is already another resource in the depot
     */
    public void add(Map<Resource, Integer> addedResourceMap) throws InvalidAdditionException {
        checkAdd(addedResourceMap);
        uncheckedAdd(addedResourceMap);
    }

    /**
     * This method removes from toBeRemovedResourceMap the maximum quantity of resources that are both in depot and in the passed map
     * @param toBeRemovedResourceMap This map contains the resources that are not found to be stored in a depot yet
     */
    public void checkAvailability(Map<Resource, Integer> toBeRemovedResourceMap){
        //Returns if depot is empty
        if(!mapResource.keySet().iterator().hasNext()) return;
        Resource resource = mapResource.keySet().iterator().next();
        //Returns if the resource in depot is not to be removed
        if (toBeRemovedResourceMap.get(resource) == null) return;
        //Checks availability and removes the resources available from toBeRemovedResourceMap
        if (mapResource.get(resource) >= toBeRemovedResourceMap.get(resource))
            toBeRemovedResourceMap.remove(resource);
        else
            toBeRemovedResourceMap.merge(resource, -mapResource.get(resource), Integer::sum);
    }

    /**
     * This method removes from toBeRemovedResourceMap and from the depot the maximum quantity of resources that are both in depot and in the passed map,
     * must be called only after performing checks with checkAvailability
     * @param toBeRemovedResourceMap This map contains the resources that have to be removed from WarehouseDepots
     */
    public void uncheckedRemove(Map<Resource, Integer> toBeRemovedResourceMap){
        //Checking if the depot is empty
        if(!mapResource.keySet().iterator().hasNext()) return;
        Resource resource = mapResource.keySet().iterator().next();
        //Checking if the resource in depot is not to be removed
        if (toBeRemovedResourceMap.get(resource) == null) return;

        int quantityToRemove = toBeRemovedResourceMap.get(resource);
        int quantityInDepot = mapResource.get(resource);

        //Removing as many resources as possible and updating toBeRemovedResourceMap
        if (quantityToRemove == quantityInDepot){
            mapResource.remove(resource);
            toBeRemovedResourceMap.remove(resource);
        }
        else if (quantityToRemove > quantityInDepot){
            mapResource.remove(resource);
            toBeRemovedResourceMap.merge(resource, -quantityInDepot, Integer::sum);
        }
        else{
            mapResource.merge(resource, -quantityToRemove, Integer::sum);
            toBeRemovedResourceMap.remove(resource);
        }
    }

    /**
     * Method used to get the quantity of the resource in the depot
     * @return The quantity of the resource in the depot if there is one, otherwise 0
     */
    public int getNumberResources(){
        int total = 0;
        //Iterating in the Collection of values of the Map
        for (int singleQuantity : mapResource.values()){
            total += singleQuantity;
        }
        return total;
    }


}
