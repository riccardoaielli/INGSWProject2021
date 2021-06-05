package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

public class LorenzoBlackCrossUpdate extends MessageToClient{
    private final int blackCrossPosition;
    public LorenzoBlackCrossUpdate(String nickname, int blackCrossPosition) {
        super(nickname, MessageType.LORENZO_BLACK_CROSS_UPDATE);
        this.blackCrossPosition = blackCrossPosition;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateBlackcrossPosition(blackCrossPosition);
    }
}
