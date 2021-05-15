package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

public class ActivateBasicProductionMessage extends MessageToServer{
    private Map<Resource,Integer> costStrongbox;
    private Map<Resource,Integer> costWarehouseDepot;
    private Resource resource;

    public ActivateBasicProductionMessage(String nickname, Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot, Resource resource) {
        super(nickname, MessageType.ACTIVATE_BASIC_PRODUCTION);
        this.costStrongbox = costStrongbox;
        this.costWarehouseDepot = costWarehouseDepot;
        this.resource = resource;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.activateBasicProductionMessage(view, getNickname(), costStrongbox,  costWarehouseDepot, resource);
    }
}
