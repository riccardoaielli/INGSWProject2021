package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

public class LorenzoDrawUpdate extends MessageToClient{
    private final int row;
    private final int column;
    public LorenzoDrawUpdate(String nickname,int row,int column) {
        super(nickname, MessageType.LORENZO_DRAW_UPDATE);
        this.row = row;
        this.column = column;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateLorenzoDraw(getNickname(),row,column);
    }
}
