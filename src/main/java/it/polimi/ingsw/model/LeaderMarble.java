package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.NotEnoughWhiteMarblesException;

import java.util.HashMap;

public class LeaderMarble extends LeaderCard{
    private Marble marble;
    private WhiteMarble whiteMarble;

    public LeaderMarble(int victoryPoints, Requirement requirement, Marble marble) {
        super(victoryPoints, requirement);
        this.marble = marble;
        this.whiteMarble = WhiteMarble.getInstance();
    }

    @Override
    public void abilityDiscount(HashMap<Resource, Integer> resources) {

    }

    @Override
    public void abilityDepot() {

    }

    /**
     * this method transform a number of white marbles from a map of marbles
     * @param temporaryMapMarble represents the map of marbles that contains the white marbles to transform
     * @param numOfTransformation is the number of marbles that needs to be transformed
     * @throws NotEnoughWhiteMarblesException this exception is thrown when there are not enough white marbles in the given map of marbles
     */
    @Override
    public void abilityMarble(HashMap<Marble, Integer> temporaryMapMarble, int numOfTransformation) throws NotEnoughWhiteMarblesException {
        if(temporaryMapMarble.get(whiteMarble) < numOfTransformation){
            throw new NotEnoughWhiteMarblesException();
        }
        else{
            temporaryMapMarble.replace(whiteMarble,temporaryMapMarble.get(whiteMarble) - numOfTransformation);
            temporaryMapMarble.replace(marble,temporaryMapMarble.get(marble) + numOfTransformation);
        }
    }

    @Override
    public HashMap<Resource,Integer> abilityProduction() {
        return null;
    }
}
