package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public abstract class Message {
    private final String nickname;
    MessageType messageType;

    public Message(String nickname, MessageType messageType) {
        this.messageType = messageType;
        this.nickname = nickname;
    }


    public abstract void handleMessage(Controller controller);

    public String getNickname() {
        return nickname;
    }
}
