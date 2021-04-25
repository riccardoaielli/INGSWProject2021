package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidAdditionException;
import it.polimi.ingsw.model.exceptions.InvalidRemovalException;

import java.util.HashMap;

public class Depot {
    private final int SIZE;

    private HashMap<Resource, Integer> mapResource;

    public Depot(int size) {
        SIZE = size;
        mapResource = new HashMap<>();
    }

    public int getSIZE() {
        return SIZE;
    }

    public HashMap<Resource, Integer> getMapResource() {
        return mapResource;
    }
    public void setMapResource(HashMap<Resource, Integer> mapResource) {
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
    public void checkAdd(HashMap<Resource, Integer> addedResourceMap) throws InvalidAdditionException{
        Resource resource = addedResourceMap.keySet().iterator().next();
        //Making sure there isn't another resource in the depot
        if (!(this.checkResource(resource) || mapResource.isEmpty())) throw new InvalidAdditionException("There is another resource in the depot");
        //Making sure the maximum size of depot is not exceeded
        if (!(mapResource.getOrDefault(resource, 0) + addedResourceMap.get(resource) <= SIZE)) throw new InvalidAdditionException("Not enough space");
    }

    public void uncheckedAdd(HashMap<Resource, Integer> addedResourceMap){
        Resource resource = addedResourceMap.keySet().iterator().next();
        mapResource.merge(resource, addedResourceMap.get(resource), Integer::sum);
    }

    /**
     * This method adds the passed single resource map to the map of the depot
     * @param addedResourceMap The single resource map to be added, must be a map with a single key
     * @throws InvalidAdditionException When there is not enough space or there is already another resource in the depot
     */
    public void add(HashMap<Resource, Integer> addedResourceMap) throws InvalidAdditionException {
        checkAdd(addedResourceMap);
        uncheckedAdd(addedResourceMap);
    }

    public void checkRemove(HashMap<Resource, Integer> removedResourceMap) throws InvalidRemovalException {
        Resource resource = removedResourceMap.keySet().iterator().next();
        if (mapResource.get(resource) == null) throw new InvalidRemovalException();
        if (mapResource.get(resource) < removedResourceMap.get(resource)) throw new InvalidRemovalException();
    }

    /**
     * Method to remove a specified number of a resource from the depot
     * @param removedResourceMap Must have a single key which represents the single resource to remove and the value associated represents the quantity to remove
     * @throws InvalidRemovalException When there are not enough resources to remove from the depot or when the resource is not in the depot
     */
    public void remove(HashMap<Resource, Integer> removedResourceMap) throws InvalidRemovalException {
        //Checking if there are enough resources in depot to remove
        checkRemove(removedResourceMap);
        uncheckedRemove(removedResourceMap);
    }

    public void uncheckedRemove(HashMap<Resource, Integer> removedResourceMap){
        Resource resource = removedResourceMap.keySet().iterator().next();
        if (mapResource.get(resource).equals(removedResourceMap.get(resource)))
            mapResource.remove(resource);
        else
            mapResource.merge(resource, -removedResourceMap.get(resource), Integer::sum);
    }

    /**
     * Method used to get the quantity of the resource in the depot
     * @return The quantity of the resource in the depot if there is one, otherwise 0
     */
    public int getNumberResources(){
        int total = 0;
        //Iterating in the Collection of values of the HashMap
        for (int singleQuantity : mapResource.values()){
            total += singleQuantity;
        }
        return total;
    }


}
