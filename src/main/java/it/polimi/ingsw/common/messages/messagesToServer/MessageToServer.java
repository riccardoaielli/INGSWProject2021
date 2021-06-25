package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.Message;
import it.polimi.ingsw.server.controller.Controller;

/**
 * Abstract class representing message sent to server
 */
public abstract class MessageToServer extends Message {
    /**
     * Constructor of the message
     * @param nickname the player that sends the message
     * @param messageType the type of the message
     */
    public MessageToServer(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    public abstract void handleMessage(Controller controller, View view);
}
