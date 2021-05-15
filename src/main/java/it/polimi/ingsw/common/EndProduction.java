package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public class EndProduction extends MessageToServer{
    public EndProduction(String nickname, MessageType messageType) {
        super(nickname, MessageType.END_MESSAGE);
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.endProductionMessage(view,getNickname());
    }
}
