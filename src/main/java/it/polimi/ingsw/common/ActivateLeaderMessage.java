package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public class ActivateLeaderMessage extends MessageToServer{
    private int numLeaderCard;

    public ActivateLeaderMessage(String nickname,int numLeaderCard) {
        super(nickname, MessageType.ACTIVATE_LEADER);
        this.numLeaderCard = numLeaderCard;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleActivateLeader(numLeaderCard,this.getNickname(),view);
    }
}
