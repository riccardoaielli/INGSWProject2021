package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * This class represents the message sent from the client to take marbles from a row or column of the market
 */
public class TakeFromMarketMessage extends MessageToServer {
    private int rowOrColumn;
    private int value;

    public TakeFromMarketMessage(String nickname, int rowOrColumn,int value) {
        super(nickname, MessageType.TAKE_FROM_MARKET);
        this.rowOrColumn = rowOrColumn;
        this.value = value;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleTakeFromMarketMessage(rowOrColumn,value,this.getNickname(), view);
    }
}
