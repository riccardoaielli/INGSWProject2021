package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.Message;
import it.polimi.ingsw.server.controller.Controller;

/**
 * Abstract class representing message sent to server
 */
public abstract class MessageToServer extends Message {

    public MessageToServer(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }

    public abstract void handleMessage(Controller controller, View view);
}
