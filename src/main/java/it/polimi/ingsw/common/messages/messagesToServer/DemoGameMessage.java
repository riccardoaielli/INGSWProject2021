package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.controller.Controller;

/**
 * Message sent from the client to activate the demonstration mode in the match
 */
public class DemoGameMessage extends MessageToServer{
    public DemoGameMessage() {
        super(null,MessageType.DEMO_GAME);
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.setDemo();
    }
}
