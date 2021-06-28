package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message to communicate a client that the production requested is complete
 */
public class ProductionDoneUpdate extends MessageToClient{
    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     */
    public ProductionDoneUpdate(String nickname) {
        super(nickname, MessageType.PRODUCTION_DONE);
    }

    /**
     * Handles the phase of the client
     * @param clientView the view that activated the production
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.setFirstProductionDone(true);
        clientView.getPhase().handlePhase(clientView);
    }
}
