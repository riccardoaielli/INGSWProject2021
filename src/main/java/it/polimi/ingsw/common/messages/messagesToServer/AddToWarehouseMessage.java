package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Message sent from client to add the resources obtained from the market to the warehouse
 */
public class AddToWarehouseMessage extends MessageToServer {
    int depotLevel;
    Map<Resource,Integer> singleResourceMap;

    /**
     * Constructor of the message
     * @param nickname the nickname of the player that sent the message
     * @param depotLevel the depot to add the resource
     * @param singleResourceMap a map with the resource to add to the depot
     */
    public AddToWarehouseMessage(String nickname, int depotLevel, Map<Resource,Integer> singleResourceMap ) {
        super(nickname, MessageType.ADD_TO_WAREHOUSE);
        this.depotLevel = depotLevel;
        this.singleResourceMap = singleResourceMap;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleAddToWarehouseMessage(view, this.getNickname(), depotLevel, singleResourceMap);
    }
}
