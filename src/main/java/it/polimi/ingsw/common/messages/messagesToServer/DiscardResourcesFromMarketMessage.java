package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.controller.Controller;

/**
 * Message sent from the client to discard the resources obtained from the market
 */
public class DiscardResourcesFromMarketMessage extends MessageToServer{
    /**
     * Constructor of the message
     * @param nickname the player that sent the message
     */
    public DiscardResourcesFromMarketMessage(String nickname) {
        super(nickname, MessageType.DISCARD_RESOURCES_FROM_MARKET);
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleDiscardResourcesFromMarket(view, getNickname());
    }
}
