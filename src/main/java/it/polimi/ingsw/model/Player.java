package it.polimi.ingsw.model;

public class Player {
    private final String nickname;
    private  PersonalBoard personalBoard;
    private boolean connected;

    public Player(String nickname,PersonalBoard personalBoard) {//passiamo una personal board gia costruita
        this.nickname = nickname;
        this.personalBoard = personalBoard;
    }

    public String getNickname() {
        return nickname;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
