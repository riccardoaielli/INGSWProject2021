package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message to update the position of Lorenzo's black cross on the faith track
 */
public class LorenzoBlackCrossUpdate extends MessageToClient{
    private final int blackCrossPosition;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param blackCrossPosition the updated position of the black cross
     */
    public LorenzoBlackCrossUpdate(String nickname, int blackCrossPosition) {
        super(nickname, MessageType.LORENZO_BLACK_CROSS_UPDATE);
        this.blackCrossPosition = blackCrossPosition;
    }

    /**
     * Shows the update
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateBlackcrossPosition(blackCrossPosition);
    }
}
