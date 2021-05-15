package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;

public class DiscardedLeaderUpdate extends MessageToClient{
    int leaderPosition;
    public DiscardedLeaderUpdate(String nickname, int leaderPosition) {
        super(nickname, MessageType.DISCARDED_LEADER_UPDATE);
        this.leaderPosition = leaderPosition;
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }
}
