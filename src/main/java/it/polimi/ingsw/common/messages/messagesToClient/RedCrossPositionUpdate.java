package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message to update the position of the red cross of the client
 */
public class RedCrossPositionUpdate extends MessageToClient {
    private final int redCrossPosition;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param redCrossPosition the updated position of the red cross
     */
    public RedCrossPositionUpdate(String nickname, int redCrossPosition) {
        super(nickname, MessageType.REDCROSS_POSITION_UPDATE);
        this.redCrossPosition = redCrossPosition;
    }

    /**
     * Shows the update
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateRedcrossPosition(getNickname(), redCrossPosition);
    }
}
