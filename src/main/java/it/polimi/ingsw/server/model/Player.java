package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.View;

public class Player {
    private final String nickname;
    private  PersonalBoard personalBoard;
    private View view;
    private boolean connected;


    public Player(String nickname, PersonalBoard personalBoard, View view) {
        this.nickname = nickname;
        this.personalBoard = personalBoard;
        this.view = view;
        personalBoard.setPlayer(this);
    }

    public View getView() { return view; }

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
