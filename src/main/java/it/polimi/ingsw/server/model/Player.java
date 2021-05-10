package it.polimi.ingsw.server.model;

public class Player {
    private final String nickname;
    private  PersonalBoard personalBoard;
    private boolean connected;

    public Player(String nickname,PersonalBoard personalBoard) {
        this.nickname = nickname;
        this.personalBoard = personalBoard;
    }

    public String getNickname() {
        return nickname;
    }

    public void setConnection(boolean connected) {
        this.connected = connected;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
