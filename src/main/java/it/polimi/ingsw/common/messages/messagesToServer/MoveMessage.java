package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * Message sent from the client to move resources between a depot and a special depot
 */
public class MoveMessage extends MessageToServer {
    private final int sourceDepotNumber;
    private final int destinationDepotNumber;
    private final int quantity;

    /**
     * Constructor of the message
     * @param nickname the player that sent the message
     * @param sourceDepotNumber the depot to take the resources from
     * @param destinationDepotNumber the depot to deposit the resources
     * @param quantity the number of resources to move
     */
    public MoveMessage(String nickname, int sourceDepotNumber, int destinationDepotNumber, int quantity) {
        super(nickname, MessageType.MOVE);
        this.sourceDepotNumber = sourceDepotNumber;
        this.destinationDepotNumber = destinationDepotNumber;
        this.quantity = quantity;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleMoveMessage(view,this.getNickname(),sourceDepotNumber,destinationDepotNumber,quantity);
    }
}
