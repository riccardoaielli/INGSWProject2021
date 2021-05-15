package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;

public class PlayerTurnUpdate extends MessageToClient{
    public PlayerTurnUpdate(String nickname) {
        super(nickname, MessageType.PLAYER_TURN_UPDATE);
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }
}
