package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.controller.Controller;

public class PingMessage extends MessageToServer{
    /**
     * Constructor of the message
     */
    public PingMessage() {
        super(null, MessageType.PING);
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view       the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {

    }
}
