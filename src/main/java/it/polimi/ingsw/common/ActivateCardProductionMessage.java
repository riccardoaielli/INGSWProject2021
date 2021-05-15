package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

public class ActivateCardProductionMessage extends MessageToServer{
    private Map<Resource,Integer> costStrongbox;
    private Map<Resource,Integer> costWarehouseDepot;
    private int indexDevelopmentCardSpace;

    public ActivateCardProductionMessage(String nickname, Map<Resource,Integer> costStrongbox, Map<Resource,Integer> costWarehouseDepot, int indexDevelopmentCardSpace) {
        super(nickname, MessageType.ACTIVATE_CARD_PRODUCTION);
        this.costStrongbox = costStrongbox;
        this.costWarehouseDepot = costWarehouseDepot;
        this.indexDevelopmentCardSpace = indexDevelopmentCardSpace;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.activateCardProductionMessage(view, getNickname(), costStrongbox, costWarehouseDepot, indexDevelopmentCardSpace);
    }
}
