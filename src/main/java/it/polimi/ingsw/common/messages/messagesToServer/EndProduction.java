package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

public class EndProduction extends MessageToServer {
    public EndProduction(String nickname, MessageType messageType) {
        super(nickname, MessageType.END_PRODUCTION);
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.endProductionMessage(view,getNickname());
    }
}
