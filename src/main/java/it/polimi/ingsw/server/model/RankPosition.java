package it.polimi.ingsw.server.model;

public class RankPosition {
    private final String nickname;
    private final int victoryPoints;

    public RankPosition(String nickname, int victoryPoints) {
        this.nickname = nickname;
        this.victoryPoints = victoryPoints;
    }
}
