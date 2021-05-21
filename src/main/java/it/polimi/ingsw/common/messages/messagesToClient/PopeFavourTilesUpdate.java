package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

import java.util.ArrayList;

public class PopeFavourTilesUpdate extends MessageToClient {
    private final ArrayList<Integer> popeFavourTiles;

    public PopeFavourTilesUpdate(String nickname, ArrayList<Integer> popeFavourTiles) {
        super(nickname, MessageType.POPE_FAVOUR_TILES_UPDATE);
        this.popeFavourTiles = popeFavourTiles;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdatePopeFavourTiles(getNickname(),popeFavourTiles);
    }
}