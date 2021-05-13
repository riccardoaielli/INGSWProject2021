package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public class CreateMatchReplyMessage extends MessageToServer{
    int numOfPlayers;

    public CreateMatchReplyMessage(String nickname, int numOfPlayers) {
        super(nickname, MessageType.CREATE_MATCH_REPLY);
        this.numOfPlayers = numOfPlayers;
    }

    @Override
    public void handleMessage(Controller controller, View virtualView) {
        controller.handleCreateMatchReplyMessage(numOfPlayers, this.getNickname(),virtualView);
    }
}
