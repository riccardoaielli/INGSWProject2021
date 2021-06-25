package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * Message sent from the client to activate a leader card
 */
public class ActivateLeaderMessage extends MessageToServer {
    private final int numLeaderCard;

    /**
     * Constructor of the message
     * @param nickname the player that sent the message
     * @param numLeaderCard the index of the leader card to activate
     */
    public ActivateLeaderMessage(String nickname,int numLeaderCard) {
        super(nickname, MessageType.ACTIVATE_LEADER);
        this.numLeaderCard = numLeaderCard;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleActivateLeader(numLeaderCard,this.getNickname(),view);
    }
}
