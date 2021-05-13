package it.polimi.ingsw.common;

import it.polimi.ingsw.client.MessageHandler;

public abstract class MessageToClient extends Message{
    public MessageToClient(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }
    public abstract void handleMessage(MessageHandler controller);
}
