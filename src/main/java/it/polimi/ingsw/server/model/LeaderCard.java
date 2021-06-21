package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidLeaderAction;
import it.polimi.ingsw.server.model.exceptions.NotEnoughWhiteMarblesException;

import java.util.Map;

/**
 * this class represent the abstract leader card
 */
public abstract class LeaderCard {
    private int id;
    private int victoryPoints;
    private Requirement requirement;
    private Boolean active;

    public LeaderCard(int victoryPoints, Requirement requirement) {
        this.victoryPoints = victoryPoints;
        this.requirement = requirement;
        this.active = false;
    }

    /**
     * @return the id of the card
     */
    public int getId() {
        return id;
    }

    /**
     * activate the leader card
     */
    public void activate(){
        active = true;
    }

    /**
     * method to check if a card is active
     * @return true if the card is active
     */
    public Boolean isActive(){
        return active;
    }

    /**
     * getter for victory points
     * @return the victory points of the card
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * getter for card requirements
     * @return the requirement of the card
     */
    public Requirement getRequirement() {
        return requirement;
    }

    /**
     * Method to use the discount ability of the card
     * @param resources the map to discount
     * @throws InvalidLeaderAction when the card does not have this ability
     */
    public void abilityDiscount(Map<Resource,Integer> resources) throws InvalidLeaderAction {
        throw new InvalidLeaderAction();
    }

    /**
     * Method to use the depot ability of the card
     * @param warehouseDepots the warehouse depot where the new depot will be added
     * @throws InvalidLeaderAction when the card does not have this ability
     */
    public  void abilityDepot(WarehouseDepots warehouseDepots) throws InvalidLeaderAction{
        throw new InvalidLeaderAction();
    }

    /**
     * Method to use the marble ability of the card
     * @param temporaryMapMarble the map that contains the marbles to transform
     * @param numOfTransformation the number of marbles to transform
     * @throws NotEnoughWhiteMarblesException when the map does not contains enough marbles
     * @throws InvalidLeaderAction when the card does not have this ability
     */
    public  void abilityMarble(Map<Marble,Integer> temporaryMapMarble, int numOfTransformation) throws NotEnoughWhiteMarblesException, InvalidLeaderAction{
        throw new InvalidLeaderAction();
    }

    /**
     * Method to use the production ability of the card
     * @return the power of production of the card
     * @throws InvalidLeaderAction when the card does not have this ability
     */
    public  PowerOfProduction abilityProduction() throws InvalidLeaderAction{
        throw new InvalidLeaderAction();
    }

}
