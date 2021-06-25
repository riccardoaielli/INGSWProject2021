package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Message to let the player select the initial resources
 */
public class ChooseInitialResourcesMessage extends MessageToServer {
    Map<Resource, Integer> resourceIntegerMap;

    /**
     * Constructor of the message
     * @param nickname the nickname of the player
     * @param resourceIntegerMap map of resources to get for the player
     */
    public ChooseInitialResourcesMessage(String nickname, Map<Resource, Integer> resourceIntegerMap) {
        super(nickname, MessageType.CHOOSE_INITIAL_RESOURCES);
        this.resourceIntegerMap = resourceIntegerMap;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleChooseInitialResourcesMessage(view, this.getNickname(), resourceIntegerMap);
    }
}
