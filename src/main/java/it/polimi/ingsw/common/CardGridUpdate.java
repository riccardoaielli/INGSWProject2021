package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;


public class CardGridUpdate extends MessageToClient{
    int[][] cardGridMatrixUpdate;

    public CardGridUpdate(int[][] cardGridMatrixUpdate) {
        super(null, MessageType.CARDGRID_UPDATE);
        this.cardGridMatrixUpdate = cardGridMatrixUpdate;
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }
}
