package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.exceptions.NotEnoughWhiteMarblesException;

import java.util.HashMap;

public class LeaderMarble extends LeaderCard{
    private Marble marble;
    private Marble whiteMarble;

    public LeaderMarble(int victoryPoints, Requirement requirement, Marble marble) {
        super(victoryPoints, requirement);
        this.marble = marble;
        this.whiteMarble = Marble.WHITEMARBLE;
    }

    /**
     * this method transform a number of white marbles from a map of marbles
     * @param temporaryMapMarble represents the map of marbles that contains the white marbles to transform
     * @param numOfTransformation is the number of marbles that needs to be transformed
     * @throws NotEnoughWhiteMarblesException this exception is thrown when there are not enough white marbles in the given map of marbles
     */
    @Override
    public void abilityMarble(HashMap<Marble, Integer> temporaryMapMarble, int numOfTransformation) throws NotEnoughWhiteMarblesException {
        if(!temporaryMapMarble.containsKey(whiteMarble) || temporaryMapMarble.get(whiteMarble) < numOfTransformation){
            throw new NotEnoughWhiteMarblesException();
        }
        else{
            temporaryMapMarble.replace(whiteMarble,temporaryMapMarble.get(whiteMarble) - numOfTransformation);
            temporaryMapMarble.merge(marble,numOfTransformation,Integer::sum);
        }
    }

}
