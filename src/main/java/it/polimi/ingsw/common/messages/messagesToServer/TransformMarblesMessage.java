package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * This class represents the message sent from the client when it wants to transform the marbles obtained from the market into resources
 */
public class TransformMarblesMessage extends MessageToServer {

    /**
     * Constructor of the message
     * @param nickname the player that sent the message
     */
    public TransformMarblesMessage(String nickname) {
        super(nickname, MessageType.TRANSFORM_MARBLES);
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) { controller.handleTransformMarblesMessage(view, this.getNickname()); }
}
