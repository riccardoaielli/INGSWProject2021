package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Message to update the temporary resources of the client
 */
public class TemporaryResourceMapUpdate extends MessageToClient {
    private final Map<Resource, Integer> temporaryMapResource;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param temporaryMapResource the updated map of resources
     */
    public TemporaryResourceMapUpdate(String nickname, Map<Resource, Integer> temporaryMapResource) {
        super(nickname, MessageType.TEMPORARY_RESOURCE_MAP_UPDATE);
        this.temporaryMapResource = temporaryMapResource;
    }

    /**
     * Shows the update and changes the localPhase
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
       clientView.showUpdatedTemporaryMapResource(this.getNickname(), temporaryMapResource);
       if(clientView.getNickname().equals(getNickname())){
           if (!temporaryMapResource.isEmpty()){
               clientView.setPhase(LocalPhase.ADD_TO_WAREHOUSE);
               clientView.getPhase().handlePhase(clientView);
           }
        }
    }
}
