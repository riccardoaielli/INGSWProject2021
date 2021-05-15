package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.server.model.enumerations.Resource;

public class AddSpecialDepotUpdate extends MessageToClient{
    Resource depotResourceType;

    public AddSpecialDepotUpdate(String nickname, Resource depotResourceType) {
        super(nickname, MessageType.ADD_SPECIALDEPOT_UPDATE);
        this.depotResourceType = depotResourceType;
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }
}
