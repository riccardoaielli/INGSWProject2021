package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.messages.Message;

/**
 * Abstract class representing messages sent to client
 */
public abstract class MessageToClient extends Message {
    public MessageToClient(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }
    public abstract void handleMessage(ClientView clientView);
}
