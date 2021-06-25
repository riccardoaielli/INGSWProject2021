package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message to update the action performed by Lorenzo
 */
public class LorenzoDrawUpdate extends MessageToClient{
    private final int row;
    private final int column;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param row the row in the card grid
     * @param column the column in the card grid
     */
    public LorenzoDrawUpdate(String nickname,int row,int column) {
        super(nickname, MessageType.LORENZO_DRAW_UPDATE);
        this.row = row;
        this.column = column;
    }

    /**
     * Shows the update
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateLorenzoDraw(getNickname(),row,column);
    }
}
