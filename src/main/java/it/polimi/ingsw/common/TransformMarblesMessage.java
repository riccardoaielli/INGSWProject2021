package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

/**
 * This class represents the message sent from the client when it wants to transform the marbles obtained from the market into resources
 */
public class TransformMarblesMessage extends MessageToServer{

    public TransformMarblesMessage(String nickname) {
        super(nickname, MessageType.TRANSFORM_MARBLES);
    }

    @Override
    public void handleMessage(Controller controller, View view) { controller.handleTransformMarblesMessage(view, this.getNickname()); }
}
