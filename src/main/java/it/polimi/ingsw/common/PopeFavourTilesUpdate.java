package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;

import java.util.ArrayList;

public class PopeFavourTilesUpdate extends MessageToClient{
    private ArrayList<Integer> popeFavourTiles;

    public PopeFavourTilesUpdate(String nickname, ArrayList<Integer> popeFavourTiles) {
        super(nickname, MessageType.POPE_FAVOUR_TILES_UPDATE);
        this.popeFavourTiles = popeFavourTiles;
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }
}
