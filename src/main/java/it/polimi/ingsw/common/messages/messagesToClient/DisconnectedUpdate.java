package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message to update if a client disconnected from the server
 */
public class DisconnectedUpdate extends MessageToClient{

    /**
     * Constructor of the message
     * @param nickname the player that disconnected
     */
    public DisconnectedUpdate(String nickname) {
        super(nickname, MessageType.DISCONNECTED_UPDATE);
    }

    /**
     * Shows the update
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.closeGame(getNickname() + " disconnected, game ends");
    }
}
