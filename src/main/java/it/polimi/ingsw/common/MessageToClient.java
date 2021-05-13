package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;

/**
 * Abstract class representing messages sent to client
 */
public abstract class MessageToClient extends Message{
    public MessageToClient(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }
    public abstract void handleMessage(ClientView clientView);
}
