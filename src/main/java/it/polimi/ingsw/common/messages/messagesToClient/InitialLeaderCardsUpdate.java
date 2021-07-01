package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

import java.util.ArrayList;

/**
 * Message to update the leader cards of the client
 */
public class InitialLeaderCardsUpdate extends MessageToClient {
    ArrayList<Integer> initialLeaderCardsID;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param initialLeaderCardsID the IDs of the four initial leader cards
     */
    public InitialLeaderCardsUpdate(String nickname, ArrayList<Integer> initialLeaderCardsID) {
        super(nickname, MessageType.INITIAL_LEADERCARDS_UPDATE);
        this.initialLeaderCardsID = initialLeaderCardsID;
    }

    /**
     * Shows the update and changes the local phase
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.setPhase(LocalPhase.LEADER_CHOICE);
        clientView.showUpdateInitialLeaderCard(initialLeaderCardsID);
    }
}
