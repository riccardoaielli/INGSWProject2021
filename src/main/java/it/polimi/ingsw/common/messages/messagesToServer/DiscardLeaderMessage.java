package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * Message sent from the client to discard a leader card
 */
public class DiscardLeaderMessage extends MessageToServer {
    private int numLeaderCard;

    public DiscardLeaderMessage(String nickname, int numLeaderCard) {
        super(nickname, MessageType.DISCARD_LEADER);
        this.numLeaderCard = numLeaderCard;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleDiscardLeaderMessage(view,getNickname(),numLeaderCard);
    }
}
