package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message to communicate a client that it is the first client to connect to the server
 */
public class FirstConnectedUpdate extends MessageToClient{
    private final Boolean firstConnection;

    /**
     * Constructor of the message
     * @param firstConnection true if the other players aren't connected
     */
    public FirstConnectedUpdate(Boolean firstConnection) {
        super(null, MessageType.FIRST_CONNECTION_UPDATE);
        this.firstConnection = firstConnection;
    }

    /**
     * Shows the update and changes the local phase
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        if(firstConnection)
            clientView.setPhase(LocalPhase.FIRST_PLAYER);
        else
            clientView.setPhase(LocalPhase.NICKNAME);
        clientView.getPhase().handlePhase(clientView);
    }
}
