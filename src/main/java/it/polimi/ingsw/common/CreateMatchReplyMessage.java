package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

/**
 * This class represents the message sent from the client to create a match with a number of players and add a new player nickname to the match
 */
public class CreateMatchReplyMessage extends MessageToServer{
    int numOfPlayers;

    public CreateMatchReplyMessage(String nickname, int numOfPlayers) {
        super(nickname, MessageType.CREATE_MATCH_REPLY);
        this.numOfPlayers = numOfPlayers;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleCreateMatchReplyMessage(numOfPlayers, this.getNickname(), view);
    }
}
