package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

public class DiscardLeaderMessage extends MessageToServer {
    private int numLeaderCard;

    public DiscardLeaderMessage(String nickname, int numLeaderCard) {
        super(nickname, MessageType.DISCARD_LEADER);
        this.numLeaderCard = numLeaderCard;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleDiscardLeaderMessage(view,getNickname(),numLeaderCard);
    }
}
