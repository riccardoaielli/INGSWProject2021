package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Message sent form the client to activate the production from a development card
 */
public class ActivateCardProductionMessage extends MessageToServer {
    private final Map<Resource,Integer> costStrongbox;
    private final Map<Resource,Integer> costWarehouseDepot;
    private final int indexDevelopmentCardSpace;

    /**
     * Constructor of the message
     * @param nickname the player that sent the message
     * @param costStrongbox a map of resources to take from the strongbox
     * @param costWarehouseDepot a map of resources to take from the warehouse
     * @param indexDevelopmentCardSpace the index of the card to use
     */
    public ActivateCardProductionMessage(String nickname, Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot, int indexDevelopmentCardSpace) {
        super(nickname, MessageType.ACTIVATE_CARD_PRODUCTION);
        this.costStrongbox = costStrongbox;
        this.costWarehouseDepot = costWarehouseDepot;
        this.indexDevelopmentCardSpace = indexDevelopmentCardSpace;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleActivateCardProductionMessage(view, getNickname(), costStrongbox, costWarehouseDepot, indexDevelopmentCardSpace);
    }
}
