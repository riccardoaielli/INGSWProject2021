package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message sent to client with an error message to display
 */
public class ErrorMessage extends MessageToClient {
    private String errorString;
    public ErrorMessage(String nickname, String errorString) {
        super(nickname, MessageType.ERROR);
        this.errorString = errorString;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showError(errorString);
        clientView.getPhase().handlePhase(clientView);
    }
}
