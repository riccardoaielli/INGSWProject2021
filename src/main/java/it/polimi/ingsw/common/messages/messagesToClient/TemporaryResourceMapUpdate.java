package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.HashMap;
import java.util.Map;

public class TemporaryResourceMapUpdate extends MessageToClient {
    private Map<Resource, Integer> temporaryMapResource;

    public TemporaryResourceMapUpdate(String nickname, Map<Resource, Integer> temporaryMapResource) {
        super(nickname, MessageType.TEMPORARY_RESOURCE_MAP_UPDATE);
        this.temporaryMapResource = temporaryMapResource;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.getLocalModel().getLocalPlayer().setTemporaryMarbles(new HashMap<>());
        clientView.showUpdatedTemporaryMapResource(this.getNickname(), temporaryMapResource);
        if(clientView.getNickname().equals(getNickname())){
            clientView.setPhase(LocalPhase.ADD_TO_WAREHOUSE);
            if (!temporaryMapResource.isEmpty()){
                clientView.getPhase().handlePhase(clientView);
            }
        }
    }
}
