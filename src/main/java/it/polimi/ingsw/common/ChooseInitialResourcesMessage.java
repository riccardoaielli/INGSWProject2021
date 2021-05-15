package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Message to let the player select the initial resources
 */
public class ChooseInitialResourcesMessage extends MessageToServer{
    Map<Resource, Integer> resourceIntegerMap;

    public ChooseInitialResourcesMessage(String nickname, Map<Resource, Integer> resourceIntegerMap) {
        super(nickname, MessageType.CHOOSE_INITIAL_RESOURCES);
        this.resourceIntegerMap = resourceIntegerMap;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleChooseInitialResourcesMessage(view, this.getNickname(), resourceIntegerMap);
    }
}
