package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidParameterException;

import java.util.ArrayList;

public class Match {
    private int matchID;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private boolean lastRound;


    public void setup(){

    }

    public void nextPlayer(){

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method activates the vatican report on all players on a specific tile
     * @param tileNumber is the tile to activate
     */
    public void vaticanReport(int tileNumber){
        players.forEach(x -> x.getPersonalBoard().activateVaticanReport(tileNumber));
    }

    public void setLastRound() {
        lastRound = true;
    }

    public void endGame() {

    }

    /**
     * This method moves the faith marker for all the players out of the current player
     * @param positions is the number of steps to make on the faith track for each player
     */
    public void moveFaithMarkerAll(int positions){
        players.stream().filter(x -> x != currentPlayer).forEach(x -> {
            try {
                x.getPersonalBoard().moveFaithMarker(positions);
            } catch (InvalidParameterException invalidParameterException) {
                invalidParameterException.printStackTrace();
            }
        });
        players.stream().filter(x -> x != currentPlayer).forEach(x -> x.getPersonalBoard().checkVaticanReport());
    }

}
