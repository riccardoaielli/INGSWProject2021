package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Message to update the strongbox of the client
 */
public class StrongboxUpdate extends MessageToClient {
    private final Map<Resource, Integer> strongbox;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param strongbox the updated strongbox structure
     */
    public StrongboxUpdate(String nickname, Map<Resource, Integer> strongbox) {
        super(nickname, MessageType.STRONGBOX_UPDATE);
        this.strongbox = strongbox;
    }

    /**
     * Shows the update
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) { clientView.showUpdatedStrongbox(this.getNickname(), strongbox);
    }
}
