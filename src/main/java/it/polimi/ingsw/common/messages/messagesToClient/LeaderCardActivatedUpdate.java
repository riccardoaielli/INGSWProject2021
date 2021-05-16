package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

public class LeaderCardActivatedUpdate extends MessageToClient {
    int numLeadercard;
    int leaderCardID;

    public LeaderCardActivatedUpdate(String nickname, int numLeadercard, int leaderCardID) {
        super(nickname, MessageType.LEADERCARD_ACTIVATED_UPDATE);
        this.numLeadercard = numLeadercard;
        this.leaderCardID = leaderCardID;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateLeaderCardActivatedUpdate(getNickname(),numLeadercard,leaderCardID);
    }
}
