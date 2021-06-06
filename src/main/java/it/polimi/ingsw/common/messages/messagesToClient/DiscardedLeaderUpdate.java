package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

public class DiscardedLeaderUpdate extends MessageToClient {
    int leaderPosition;
    public DiscardedLeaderUpdate(String nickname, int leaderPosition) {
        super(nickname, MessageType.DISCARDED_LEADER_UPDATE);
        this.leaderPosition = leaderPosition;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateDiscardedLeaderUpdate(getNickname(),leaderPosition);
        clientView.setPhase(LocalPhase.MENU);
        clientView.getPhase().handlePhase(clientView);
    }
}
