package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public class NicknameReplyMessage extends MessageToServer{

    public NicknameReplyMessage(String nickname) {
        super(nickname, MessageType.NICKNAME_REPLY);
    }

    @Override
    public void handleMessage(Controller controller, View virtualView) {
        controller.handleNicknameReplyMessage(this.getNickname(),virtualView);
    }
}

