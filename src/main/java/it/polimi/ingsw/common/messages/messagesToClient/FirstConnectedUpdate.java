package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

public class FirstConnectedUpdate extends MessageToClient{
    private Boolean firstConnection;
    public FirstConnectedUpdate(Boolean firstConnection) {
        super(null, MessageType.FIRST_CONNECTION_UPDATE);
        this.firstConnection = firstConnection;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        if(firstConnection)
            clientView.getLocalModel().setPhase(LocalPhase.FIRST_PLAYER);
        else
            clientView.getLocalModel().setPhase(LocalPhase.NICKNAME);
        clientView.getLocalModel().getPhase().handlePhase(clientView);
    }
}
