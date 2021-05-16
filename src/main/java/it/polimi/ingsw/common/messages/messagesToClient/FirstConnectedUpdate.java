package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

public class FirstConnectedUpdate extends MessageToClient{
    private Boolean firstConnection;
    public FirstConnectedUpdate(Boolean firstConnection) {
        super(null, MessageType.FIRST_CONNECTION_UPDATE);
        this.firstConnection = firstConnection;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateFirstConnection(firstConnection);
    }
}
