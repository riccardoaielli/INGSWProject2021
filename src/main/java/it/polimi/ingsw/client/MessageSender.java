package it.polimi.ingsw.client;

import it.polimi.ingsw.common.messages.messagesToServer.MessageToServer;

public interface MessageSender {
    void sendMessage(MessageToServer message);
}
