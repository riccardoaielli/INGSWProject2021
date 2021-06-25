package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * This class represents the message sent from the client to create a match with a number of players and add a new player nickname to the match
 */
public class CreateMatchReplyMessage extends MessageToServer {
    int numOfPlayers;

    /**
     * Constructor of the message
     * @param nickname the nickname of the player
     * @param numOfPlayers the number of players to create the match
     */
    public CreateMatchReplyMessage(String nickname, int numOfPlayers) {
        super(nickname, MessageType.CREATE_MATCH_REPLY);
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleCreateMatchReplyMessage(numOfPlayers, this.getNickname(), view);
    }
}
