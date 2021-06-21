package it.polimi.ingsw.server.model;

/**
 * This class represents a row of the final rank of the game
 */
public class RankPosition {
    private final String nickname;
    private final int victoryPoints;

    public RankPosition(String nickname, int victoryPoints) {
        this.nickname = nickname;
        this.victoryPoints = victoryPoints;
    }

    /**
     * Getter for the nickname of the player in a row
     * @return the player of the row
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * toString used to print the row of the rank
     * @return a string with the player and its victory points
     */
    @Override
    public String toString() {
        return (nickname + ": " + victoryPoints);
    }
}
