package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;


public class NicknameReplyMessage extends MessageToServer{
    private String nickname;

    public NicknameReplyMessage(String nickname, MessageType messageType) {
        super(nickname, messageType);
    }

    @Override
    public void handleMessage(Controller controller, View virtualView) {
        controller.handleNicknameReplyMessage(nickname);
    }
}

