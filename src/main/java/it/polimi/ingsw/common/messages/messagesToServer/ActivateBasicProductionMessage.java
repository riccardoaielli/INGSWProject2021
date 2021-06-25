package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Message sent from the client to activate the basic production
 */
public class ActivateBasicProductionMessage extends MessageToServer {
    private final Map<Resource,Integer> costStrongbox;
    private final Map<Resource,Integer> costWarehouseDepot;
    private final Resource resource;

    /**
     * Constructor of the message
     * @param nickname the player that sent the message
     * @param costStrongbox a map of resources to take from the strongbox
     * @param costWarehouseDepot a map of resources to take from the warehouse
     * @param resource the resource to produce
     */
    public ActivateBasicProductionMessage(String nickname, Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot, Resource resource) {
        super(nickname, MessageType.ACTIVATE_BASIC_PRODUCTION);
        this.costStrongbox = costStrongbox;
        this.costWarehouseDepot = costWarehouseDepot;
        this.resource = resource;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleActivateBasicProductionMessage(view, getNickname(), costStrongbox,  costWarehouseDepot, resource);
    }
}
