package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.MatchPhase;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;

public class SoloMatch extends Match{
    Lorenzo lorenzo;
    /**
     * This constructor creates a match deserializing all the leader cards and creating the market and the card grid
     * @param matchID an int that identifies the match
     */
    public SoloMatch(int matchID) throws InvalidParameterException {
        super(matchID, 1);
        lorenzo = new Lorenzo(this.getCardGrid());
        lorenzo.addObserver(this);
        lorenzo.addObserverList(this.getMessageObservers());
    }

    @Override
    public void nextPlayer() {
        if(getMatchPhase() == MatchPhase.STANDARDROUND)
            lorenzo.draw();
        super.nextPlayer();
    }

    @Override
    public void moveFaithMarkerAll(int positions) {
        lorenzo.moveFaithMarker(positions);
    }

    @Override
    public void update() {
        super.update();
        endGame();
    }
}
