package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public class SwapMessage extends MessageToServer{
    private int depot1;
    private int depot2;

    public SwapMessage(String nickname, int depot1, int depot2) {
        super(nickname, MessageType.SWAP);
        this.depot1 = depot1;
        this.depot2 = depot2;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleSwapMessage(view,this.getNickname(),depot1,depot2);
    }
}
