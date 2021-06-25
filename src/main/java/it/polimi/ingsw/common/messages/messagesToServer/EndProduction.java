package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * Message sent from the client to ask the model to end the production phase
 */
public class EndProduction extends MessageToServer {
    /**
     * Constructor of the message
     * @param nickname the player that sent the message
     */
    public EndProduction(String nickname) {
        super(nickname, MessageType.END_PRODUCTION);
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleEndProductionMessage(view,getNickname());
    }
}
