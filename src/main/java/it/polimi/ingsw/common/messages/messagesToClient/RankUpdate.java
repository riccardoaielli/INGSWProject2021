package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.RankPosition;

import java.util.ArrayList;

/**
 * Message to communicate the final rank at the end of the game
 */
public class RankUpdate extends MessageToClient{
    private final ArrayList<RankPosition> rank;

    /**
     * Constructor of the message
     * @param rank the final rank
     */
    public RankUpdate(ArrayList<RankPosition> rank) {
        super(null, MessageType.RANK_UPDATE);
        this.rank = rank;
    }

    /**
     * Shows the update
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateRank(getNickname(),rank);
    }
}
