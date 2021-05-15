package it.polimi.ingsw.client;

import it.polimi.ingsw.common.MessageToServer;
import it.polimi.ingsw.common.View;

public class LocalSender implements MessageSender{

    private ClientView clientView;
    public LocalSender(ClientView view) {
        this.clientView = view;
    }

    /**
     * Sends message to ClientSocket
     * @param message, is the message to be sent
     */
    @Override
    public void sendMessage(MessageToServer message) {
        //message.handleMessage(,clientView);// param controller e view
    }
}
