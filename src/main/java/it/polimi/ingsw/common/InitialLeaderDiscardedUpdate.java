package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;

public class InitialLeaderDiscardedUpdate extends MessageToClient{
    private int indexLeaderCard1;
    private int indexLeaderCard2;
    public InitialLeaderDiscardedUpdate(String nickname, int indexLeaderCard1, int indexLeaderCard2) {
        super(nickname, MessageType.INITIAL_LEADER_DISCARDED_UPDATE);
        this.indexLeaderCard1 = indexLeaderCard1;
        this.indexLeaderCard2 = indexLeaderCard2;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showInitialLeaderCardDiscard(this.getNickname(), indexLeaderCard1, indexLeaderCard2);
    }
}
