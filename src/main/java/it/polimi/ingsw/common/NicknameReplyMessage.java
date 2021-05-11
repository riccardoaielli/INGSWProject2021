package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public class NicknameReplyMessage extends Message{
    private String nickname;
    @Override
    public void handleMessage(Controller controller) {
        controller.handleNicknameReplyMessage(nickname);
    }
}
