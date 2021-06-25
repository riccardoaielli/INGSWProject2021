package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message to update the card grind of the client
 */
public class CardGridUpdate extends MessageToClient {
    private final int[][] cardGridMatrixUpdate;

    /**
     * Constructor of the message
     * @param cardGridMatrixUpdate the updated structure of the card grid
     */
    public CardGridUpdate(int[][] cardGridMatrixUpdate) {
        super(null, MessageType.CARD_GRID_UPDATE);
        this.cardGridMatrixUpdate = cardGridMatrixUpdate;
    }

    /**
     * Shows the update
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateCardGridUpdate(cardGridMatrixUpdate);
    }
}
