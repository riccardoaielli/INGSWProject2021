package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

import java.util.ArrayList;

/**
 * Message to update the pope favour tiles of the client
 */
public class PopeFavourTilesUpdate extends MessageToClient {
    private final ArrayList<Integer> popeFavourTiles;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param popeFavourTiles the update structure of the pope favour tiles
     */
    public PopeFavourTilesUpdate(String nickname, ArrayList<Integer> popeFavourTiles) {
        super(nickname, MessageType.POPE_FAVOUR_TILES_UPDATE);
        this.popeFavourTiles = popeFavourTiles;
    }

    /**
     * Shows the update
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdatePopeFavourTiles(getNickname(),popeFavourTiles);
    }
}
