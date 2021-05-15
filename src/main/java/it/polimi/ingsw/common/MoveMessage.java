package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public class MoveMessage extends MessageToServer{
    private int sourceDepotNumber;
    private int destinationDepotNumber;
    private int quantity;

    public MoveMessage(String nickname, int sourceDepotNumber, int destinationDepotNumber, int quantity) {
        super(nickname, MessageType.MOVE);
        this.sourceDepotNumber = sourceDepotNumber;
        this.destinationDepotNumber = destinationDepotNumber;
        this.quantity = quantity;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleMoveMessage(view,this.getNickname(),sourceDepotNumber,destinationDepotNumber,quantity);
    }
}
