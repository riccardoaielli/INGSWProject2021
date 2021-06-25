package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message to update the leader cards draw at the beginning of the game by the client
 */
public class InitialLeaderDiscardedUpdate extends MessageToClient {
    private final int indexLeaderCard1;
    private final int indexLeaderCard2;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param indexLeaderCard1 the index of the first leader card discarded
     * @param indexLeaderCard2 the index of the second leader card discarded
     */
    public InitialLeaderDiscardedUpdate(String nickname, int indexLeaderCard1, int indexLeaderCard2) {
        super(nickname, MessageType.INITIAL_LEADER_DISCARDED_UPDATE);
        this.indexLeaderCard1 = indexLeaderCard1;
        this.indexLeaderCard2 = indexLeaderCard2;
    }

    /**
     * Shows the update and changes the local phase
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showInitialLeaderCardDiscard(this.getNickname(), indexLeaderCard1, indexLeaderCard2);
    }
}
