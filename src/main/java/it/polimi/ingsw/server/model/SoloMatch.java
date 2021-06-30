package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.MatchPhase;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;

/**
 * The match to handle a game with only one player that plays against Lorenzo
 */
public class SoloMatch extends Match{
    Lorenzo lorenzo;
    /**
     * This constructor creates a match deserializing all the leader cards and creating the market and the card grid
     * @param matchID an int that identifies the match
     */
    public SoloMatch(int matchID, boolean demo) throws InvalidParameterException {
        super(matchID, 1,demo);
        lorenzo = new Lorenzo(this);
        lorenzo.addObserver(this);
        lorenzo.addObserverList(this.getMessageObservers());
    }

    /**
     * Activates the action of Lorenzo
     */
    @Override
    public void nextPlayer() {
        if(getMatchPhase() == MatchPhase.STANDARDROUND)
            lorenzo.draw();
        super.nextPlayer();
    }

    /**
     * Move the faithMarker of Lorenzo
     * @param positions is the number of steps to make on the faith track for each player
     */
    @Override
    public void moveFaithMarkerAll(int positions) {
        lorenzo.moveFaithMarker(positions);
    }

    /**
     * Method called when the conditions to end the match are verified
     */
    @Override
    public void update() {
        super.update();
        endGame();
    }

    /**
     * Getter for testing purpose
     * @return lorenzo
     */
    public Lorenzo getLorenzo() {
        return lorenzo;
    }
}
