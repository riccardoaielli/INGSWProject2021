package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

/**
 * Abstract class extended by every message, containing nickname of the sender in case of MessageToServer or of the one whose elements in the game are changed in case of MessageToClient
 */
public abstract class Message {
    private final String nickname;
    MessageType messageType;

    public Message(String nickname, MessageType messageType) {
        this.messageType = messageType;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}