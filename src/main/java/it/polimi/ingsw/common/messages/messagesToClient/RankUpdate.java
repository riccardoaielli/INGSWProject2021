package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.RankPosition;

import java.util.ArrayList;

public class RankUpdate extends MessageToClient{
    private final ArrayList<RankPosition> rank;

    public RankUpdate(ArrayList<RankPosition> rank) {
        super(null, MessageType.RANK_UPDATE);
        this.rank = rank;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateRank(getNickname(),rank);
    }
}
