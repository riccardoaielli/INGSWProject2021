package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidLeaderAction;
import it.polimi.ingsw.server.model.exceptions.NotEnoughWhiteMarblesException;

import java.util.HashMap;

/**
 * this class represent the abstract leader card
 */
public abstract class LeaderCard {
    private int victoryPoints;
    private Requirement requirement;
    private Boolean active;

    public LeaderCard(int victoryPoints, Requirement requirement) {
        this.victoryPoints = victoryPoints;
        this.requirement = requirement;
        this.active = false;
    }

    public void activate(){
        active = true;
    }

    public Boolean isActive(){
        return active;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void abilityDiscount(HashMap<Resource,Integer> resources) throws InvalidLeaderAction {
        throw new InvalidLeaderAction();
    }

    public  void abilityDepot(WarehouseDepots warehouseDepots) throws InvalidLeaderAction{
        throw new InvalidLeaderAction();
    }

    public  void abilityMarble(HashMap<Marble,Integer> temporaryMapMarble, int numOfTransformation) throws NotEnoughWhiteMarblesException, InvalidLeaderAction{
        throw new InvalidLeaderAction();
    }

    public  PowerOfProduction abilityProduction() throws InvalidLeaderAction{
        throw new InvalidLeaderAction();
    }

}
