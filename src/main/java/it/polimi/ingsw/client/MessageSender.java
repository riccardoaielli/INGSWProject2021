package it.polimi.ingsw.client;

import it.polimi.ingsw.common.MessageToServer;

public interface MessageSender {
    void sendMessage(MessageToServer message);
}
