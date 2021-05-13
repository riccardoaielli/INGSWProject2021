package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public abstract class MessageToServer extends Message{

    public MessageToServer(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }

    public abstract void handleMessage(Controller controller);
}
