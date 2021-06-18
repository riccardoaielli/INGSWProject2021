package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

public class ProductionDoneUpdate extends MessageToClient{
    public ProductionDoneUpdate(String nickname) {
        super(nickname, MessageType.PRODUCTION_DONE);
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.getPhase().handlePhase(clientView);
    }
}
