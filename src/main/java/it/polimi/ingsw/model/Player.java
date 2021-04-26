package it.polimi.ingsw.model;

public class Player {
    private final String nickname;
    private final PersonalBoard personalBoard;

    public Player(String nickname, PersonalBoard personalBoard) {
        this.nickname = nickname;
        this.personalBoard = personalBoard;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
