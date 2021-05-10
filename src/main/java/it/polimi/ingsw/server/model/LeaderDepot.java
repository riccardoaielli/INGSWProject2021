package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Resource;

public class LeaderDepot extends LeaderCard{
    private Resource specialDepotResource;

    public LeaderDepot(int victoryPoints, Requirement requirement, Resource specialDepotResource) {
        super(victoryPoints, requirement);
        this.specialDepotResource = specialDepotResource;
    }

    @Override
    public void abilityDepot(WarehouseDepots warehouseDepots) {
        warehouseDepots.addSpecialDepot(specialDepotResource);
    }

}
