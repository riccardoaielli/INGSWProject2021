package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;


public class CardGridUpdate extends MessageToClient {
    int[][] cardGridMatrixUpdate;

    public CardGridUpdate(int[][] cardGridMatrixUpdate) {
        super(null, MessageType.CARD_GRID_UPDATE);
        this.cardGridMatrixUpdate = cardGridMatrixUpdate;
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }
}
