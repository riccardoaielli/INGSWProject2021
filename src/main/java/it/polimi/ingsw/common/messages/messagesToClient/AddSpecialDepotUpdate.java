package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.enumerations.Resource;

/**
 * Message to update the special depot of the client
 */
public class AddSpecialDepotUpdate extends MessageToClient {
    private final Resource depotResourceType;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param depotResourceType the type of the new special depot
     */
    public AddSpecialDepotUpdate(String nickname, Resource depotResourceType) {
        super(nickname, MessageType.ADD_SPECIALDEPOT_UPDATE);
        this.depotResourceType = depotResourceType;
    }

    /**
     * Shows the update
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateAddSpecialDepotUpdate(getNickname(),depotResourceType);
    }
}
