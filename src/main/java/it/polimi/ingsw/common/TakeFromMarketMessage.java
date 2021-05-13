package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.LeaderCard;

public class TakeFromMarketMessage extends MessageToServer{
    private int rowOrColumn;
    private int value;

    public TakeFromMarketMessage(String nickname, MessageType messageType, int rowOrColumn,int value) {
        super(nickname, messageType);
        this.rowOrColumn = rowOrColumn;
        this.value = value;
    }

    @Override
    public void handleMessage(Controller controller, View virtualView) {
        controller.handleTakeFromMarketMessage(rowOrColumn,value,this.getNickname(),virtualView);
    }
}
