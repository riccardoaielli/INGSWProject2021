package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.NotEnoughWhiteMarblesException;

import java.util.HashMap;

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
