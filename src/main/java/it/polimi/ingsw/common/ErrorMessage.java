package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.MessageHandler;

public class ErrorMessage extends MessageToClient{
    private String errorString;
    public ErrorMessage(String nickname, String errorString) {
        super(nickname, MessageType.ERROR);
        this.errorString = errorString;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showError(errorString);
    }
}
