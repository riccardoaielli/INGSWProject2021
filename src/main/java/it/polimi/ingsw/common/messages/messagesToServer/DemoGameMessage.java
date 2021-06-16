package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.controller.Controller;

public class DemoGameMessage extends MessageToServer{
    public DemoGameMessage() {
        super(null,MessageType.DEMO_GAME);
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.setDemo();
    }
}
