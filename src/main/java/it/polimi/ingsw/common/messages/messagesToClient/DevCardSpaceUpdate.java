package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

import java.util.ArrayList;

/**
 * Message to update the development card space of the client
 */
public class DevCardSpaceUpdate extends MessageToClient {
    private final ArrayList<ArrayList<Integer>> cardsState;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param cardsState the updated structure of the development card space
     */
    public DevCardSpaceUpdate(String nickname, ArrayList<ArrayList<Integer>> cardsState) {
        super(nickname, MessageType.DEV_CARD_SPACE_UPDATE);
        this.cardsState = cardsState;
    }

    /**
     * Shows the update
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdatedDevCardSpace(getNickname(), cardsState);
    }
}
