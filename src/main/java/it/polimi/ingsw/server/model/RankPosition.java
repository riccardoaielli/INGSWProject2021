package it.polimi.ingsw.server.model;

public class RankPosition {
    private final String nickname;
    private final int victoryPoints;

    public RankPosition(String nickname, int victoryPoints) {
        this.nickname = nickname;
        this.victoryPoints = victoryPoints;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return (nickname + ": " + victoryPoints);
    }
}
