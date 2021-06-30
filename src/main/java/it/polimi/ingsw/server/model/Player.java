package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.View;

/**
 * This class represents a player of the Match
 */
public class Player {
    private final String nickname;
    private final PersonalBoard personalBoard;
    private final View view;

    public Player(String nickname, PersonalBoard personalBoard, View view) {
        this.nickname = nickname;
        this.personalBoard = personalBoard;
        this.view = view;
        this.view.setNickname(nickname);
        personalBoard.setPlayer(this);
    }

    /**
     * Getter for the View of the player
     * @return the view of the player
     */
    public View getView() { return view; }

    /**
     * Getter for the nickname of the player
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Getter for the personal board of the player
     * @return the personalBoard of the player
     */
    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
