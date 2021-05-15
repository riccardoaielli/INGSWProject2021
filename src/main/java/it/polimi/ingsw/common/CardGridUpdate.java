package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;


public class CardGridUpdate extends MessageToClient{
    int[][] cardGridMatrixUpdate;

    public CardGridUpdate(int[][] cardGridMatrixUpdate) {
        super(null, MessageType.CARD_GRID_UPDATE);
        this.cardGridMatrixUpdate = cardGridMatrixUpdate;
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }
}
