package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public class DiscardLeaderMessage extends MessageToServer{
    private int numLeaderCard;

    public DiscardLeaderMessage(String nickname, int numLeaderCard) {
        super(nickname, MessageType.DISCARD_LEADER);
        this.numLeaderCard = numLeaderCard;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.discardLeaderMessage(view,getNickname(),numLeaderCard);
    }
}
