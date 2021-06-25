package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message sent to client with an error message to display
 */
public class ErrorMessage extends MessageToClient {
    private final String errorString;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param errorString the string that explains the error
     */
    public ErrorMessage(String nickname, String errorString) {
        super(nickname, MessageType.ERROR);
        this.errorString = errorString;
    }

    /**
     * Shows the error and handles the current phase
     * @param clientView the view that generated the error
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showError(errorString);
        clientView.getPhase().handlePhase(clientView);
    }
}
