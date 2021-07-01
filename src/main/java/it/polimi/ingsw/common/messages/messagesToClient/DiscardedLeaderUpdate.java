package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message to update the leader card discarded by the client
 */
public class DiscardedLeaderUpdate extends MessageToClient {
    private final int leaderPosition;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param leaderPosition the index of the leader that has been discarded
     */
    public DiscardedLeaderUpdate(String nickname, int leaderPosition) {
        super(nickname, MessageType.DISCARDED_LEADER_UPDATE);
        this.leaderPosition = leaderPosition;
    }

    /**
     * Shows the update and changes the local phase
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateDiscardedLeaderUpdate(getNickname(),leaderPosition);
        if(getNickname().equals(clientView.getNickname())) {
            clientView.setPhase(LocalPhase.MENU);
            clientView.getPhase().handlePhase(clientView);
        }
    }
}
