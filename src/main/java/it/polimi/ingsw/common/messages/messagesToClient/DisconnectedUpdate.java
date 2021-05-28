package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

public class DisconnectedUpdate extends MessageToClient{

    public DisconnectedUpdate(String nickname) {
        super(nickname, MessageType.DISCONNECTED_UPDATE);
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.closeGame(getNickname() + " disconnected, game ends");
    }
}
