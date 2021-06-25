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

    /**
     * Constructor of the message
     * @param nickname the player that sent the message
     * @param rowOrColumn 0 if row, 1 if column
     * @param value from 0 to 2 if row, from 0 to 3 if column
     */
    public TakeFromMarketMessage(String nickname, int rowOrColumn,int value) {
        super(nickname, MessageType.TAKE_FROM_MARKET);
        this.rowOrColumn = rowOrColumn;
        this.value = value;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleTakeFromMarketMessage(rowOrColumn,value,this.getNickname(), view);
    }
}
