package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * This class represents the message sent from the client to add a new player nickname to the match
 */
public class NicknameReplyMessage extends MessageToServer {

    /**
     * The constructor of the message
     * @param nickname the nickname of the new player
     */
    public NicknameReplyMessage(String nickname) {
        super(nickname, MessageType.ADD_PLAYER);
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleNicknameReplyMessage(this.getNickname(), view);
    }
}

