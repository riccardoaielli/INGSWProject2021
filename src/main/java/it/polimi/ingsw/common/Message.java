package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

/**
 * Abstract class extended by every message, containing nickname of the sender or recipient of the message
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