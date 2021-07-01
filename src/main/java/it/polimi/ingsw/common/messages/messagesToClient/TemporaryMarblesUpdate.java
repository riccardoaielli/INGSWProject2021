package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.enumerations.Marble;

import java.util.Map;

/**
 * Message to update the temporary marbles of the client
 */
public class TemporaryMarblesUpdate extends MessageToClient {
    private final Map<Marble,Integer> temporaryMarbles;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param temporaryMarbles the updated map of marbles
     */
    public TemporaryMarblesUpdate(String nickname,Map<Marble,Integer> temporaryMarbles) {
        super(nickname, MessageType.TEMPORARY_MARBLES_UPDATE);
        this.temporaryMarbles = temporaryMarbles;
    }

    /**
     * Shows the update and changes the local phase
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateTemporaryMarbles(getNickname(),temporaryMarbles);
        if(clientView.getNickname().equals(getNickname()) && !temporaryMarbles.isEmpty()) {
            clientView.setPhase(LocalPhase.TEMPORARY_MARBLES);
            clientView.getPhase().handlePhase(clientView);
        }
    }
}
