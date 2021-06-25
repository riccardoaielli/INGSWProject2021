package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * Message sent from the client to swap resources between depots
 */
public class SwapMessage extends MessageToServer {
    private final int depot1;
    private final int depot2;

    /**
     * Constructor of the message
     * @param nickname the player that sent the message
     * @param depot1 the first depot
     * @param depot2 the second depot
     */
    public SwapMessage(String nickname, int depot1, int depot2) {
        super(nickname, MessageType.SWAP);
        this.depot1 = depot1;
        this.depot2 = depot2;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleSwapMessage(view,this.getNickname(),depot1,depot2);
    }
}
