package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * This class represents the message sent from the client to add a new player nickname to the match
 */
public class NicknameReplyMessage extends MessageToServer {

    public NicknameReplyMessage(String nickname) {
        super(nickname, MessageType.NICKNAME_REPLY);
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleNicknameReplyMessage(this.getNickname(), view);
    }
}

