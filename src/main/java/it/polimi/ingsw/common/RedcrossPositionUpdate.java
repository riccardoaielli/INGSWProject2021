package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;

public class RedcrossPositionUpdate extends MessageToClient{
    int redcrossPosition;
    public RedcrossPositionUpdate(String nickname,int redcrossPosition) {
        super(nickname, MessageType.REDCROSS_POSITION_UPDATE);
        this.redcrossPosition = redcrossPosition;
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }
}
