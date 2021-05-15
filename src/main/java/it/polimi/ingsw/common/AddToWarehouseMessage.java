package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;
import java.util.Map;

public class AddToWarehouseMessage extends MessageToServer{
    int depotLevel;
    Map<Resource,Integer> singleResourceMap;

    public AddToWarehouseMessage(String nickname, int depotLevel, Map<Resource,Integer> singleResourceMap ) {
        super(nickname, MessageType.ADD_TO_WAREHOUSE);
        this.depotLevel = depotLevel;
        this.singleResourceMap = singleResourceMap;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleAddToWarehouseMessage(view, this.getNickname(), depotLevel, singleResourceMap);
    }
}
