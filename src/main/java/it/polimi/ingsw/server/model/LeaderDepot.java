package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Resource;

/**
 * Leader card with the depot ability
 */
public class LeaderDepot extends LeaderCard{
    private Resource specialDepotResource;

    public LeaderDepot(int victoryPoints, Requirement requirement, Resource specialDepotResource) {
        super(victoryPoints, requirement);
        this.specialDepotResource = specialDepotResource;
    }

    /**
     * Implementation of the abstract LeaderCard class
     * @param warehouseDepots the warehouse depot where the new depot will be added
     */
    @Override
    public void abilityDepot(WarehouseDepots warehouseDepots) {
        warehouseDepots.addSpecialDepot(specialDepotResource);
    }

    /**
     * Getter for the resource of the special depot created by the leader power
     * @return the resource of the special depot created
     */
    public Resource getSpecialDepotResource() {
        return specialDepotResource;
    }
}
