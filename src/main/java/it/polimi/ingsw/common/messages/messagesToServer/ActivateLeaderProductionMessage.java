package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

public class ActivateLeaderProductionMessage extends MessageToServer {
    private Map<Resource,Integer> costStrongbox;
    private Map<Resource,Integer> costWarehouseDepot;
    private int numLeaderCard;
    private Resource resource;

    public ActivateLeaderProductionMessage(String nickname, Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot, int numLeaderCard, Resource resource) {
        super(nickname, MessageType.ACTIVATE_LEADER_PRODUCTION);
        this.costStrongbox = costStrongbox;
        this.costWarehouseDepot = costWarehouseDepot;
        this.numLeaderCard = numLeaderCard;
        this.resource = resource;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleActivateLeaderProductionMessage(view,getNickname(),costStrongbox,costWarehouseDepot,numLeaderCard,resource);
    }
}
