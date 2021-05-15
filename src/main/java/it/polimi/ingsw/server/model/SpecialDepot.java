package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidAdditionException;

import java.util.Map;

public class SpecialDepot extends Depot{

    private final Resource specialResource;

    public SpecialDepot(Resource resource) {
        //Depot with size 2
        super(2);
        this.specialResource = resource;
    }

    /**
     * Checks if a resource map can be added to the depot
     * @param addedResourceMap The resource map to be added, must be a map with a single key
     * @throws InvalidAdditionException When the resource is not compatible with the special depot or there is not enough space or there is already another resource in the depot
     */
    @Override
    public void checkAdd(Map<Resource, Integer> addedResourceMap) throws InvalidAdditionException {
        if (addedResourceMap.get(this.specialResource) == null) throw new InvalidAdditionException("Resource not compatible with this special depot");
        super.checkAdd(addedResourceMap);
    }


}
