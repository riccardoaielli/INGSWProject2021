package it.polimi.ingsw.server.model;

/**
 * Leader card with the production ability
 */
public class LeaderProduction extends LeaderCard{
    private PowerOfProduction powerOfProduction;

    public LeaderProduction(int victoryPoints, Requirement requirement, PowerOfProduction powerOfProduction) {
        super(victoryPoints, requirement);
        this.powerOfProduction = powerOfProduction;
    }

    /**
     * @return the power of production of the leader card
     */
    @Override
    public PowerOfProduction abilityProduction() {
        return powerOfProduction;
    }

    public PowerOfProduction getPowerOfProduction() {
        return powerOfProduction;
    }
}
