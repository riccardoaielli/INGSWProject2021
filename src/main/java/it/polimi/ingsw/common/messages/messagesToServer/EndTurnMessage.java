package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.controller.Controller;

public class EndTurnMessage extends MessageToServer{

    public EndTurnMessage(String nickname) {
        super(nickname, MessageType.END_TURN);
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleEndTurnMessage(view, getNickname());
    }
}
