package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

public class PlayerTurnUpdate extends MessageToClient {
    public PlayerTurnUpdate(String nickname) {
        super(nickname, MessageType.PLAYER_TURN_UPDATE);
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdatePlayerTurn(getNickname());
    }
}
