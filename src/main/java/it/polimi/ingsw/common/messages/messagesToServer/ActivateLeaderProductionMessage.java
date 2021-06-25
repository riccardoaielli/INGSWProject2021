package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Message sent from the client to activate the production of a leader card
 */
public class ActivateLeaderProductionMessage extends MessageToServer {
    private Map<Resource,Integer> costStrongbox;
    private Map<Resource,Integer> costWarehouseDepot;
    private int numLeaderCard;
    private Resource resource;

    /**
     * Constructor of the message
     * @param nickname the player that sent the message
     * @param costStrongbox a map of resources to take from the strongbox
     * @param costWarehouseDepot a map of resources to take from the warehouse
     * @param numLeaderCard the in dex of the leader card to use
     * @param resource the type of resource to produce
     */
    public ActivateLeaderProductionMessage(String nickname, Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot, int numLeaderCard, Resource resource) {
        super(nickname, MessageType.ACTIVATE_LEADER_PRODUCTION);
        this.costStrongbox = costStrongbox;
        this.costWarehouseDepot = costWarehouseDepot;
        this.numLeaderCard = numLeaderCard;
        this.resource = resource;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleActivateLeaderProductionMessage(view,getNickname(),costStrongbox,costWarehouseDepot,numLeaderCard,resource);
    }
}
