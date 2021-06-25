package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * Message sent from client to server which contains the leader cards to discard in the setup phase
 */
public class DiscardInitialLeaderMessage extends MessageToServer {
    private final int indexLeaderCard1;
    private final int indexLeaderCard2;

    /**
     *Constructor of the message
     * @param indexLeaderCard1 index of one of the two leader cards to discard
     * @param indexLeaderCard2 index of one of the two leader cards to discard
     */
    public DiscardInitialLeaderMessage(String nickname, int indexLeaderCard1, int indexLeaderCard2) {
        super(nickname, MessageType.DISCARD_INITIAL_LEADER);
        this.indexLeaderCard1 = indexLeaderCard1;
        this.indexLeaderCard2 = indexLeaderCard2;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleDiscardInitialLeaderMessage(view, this.getNickname(), indexLeaderCard1, indexLeaderCard2);
    }
}
