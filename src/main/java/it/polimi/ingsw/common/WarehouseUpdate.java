package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.List;
import java.util.Map;

public class WarehouseUpdate extends MessageToClient{
    private List<Map<Resource, Integer>> depots;
    public WarehouseUpdate(String nickname, List<Map<Resource, Integer>> depots) {
        super(nickname, MessageType.WAREHOUSE_UPDATE);
        this.depots = depots;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdatedWarehouse(this.getNickname(), depots);
    }
}
