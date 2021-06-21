package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.controller.Controller;

public class DiscardResourcesFromMarketMessage extends MessageToServer{
    public DiscardResourcesFromMarketMessage(String nickname) {
        super(nickname, MessageType.DISCARD_RESOURCES_FROM_MARKET);
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleDiscardResourcesFromMarket(view, getNickname());
    }
}
