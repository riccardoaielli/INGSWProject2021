package it.polimi.ingsw.common.utils.observe;

import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.server.model.ObservableGameEnder;

/**
 * A class can implement the MessageObserver interface when it wants to be informed of changes in MessageObservable objects
 */
public interface MessageObserver {
    /**
     * Receives a message from Observable
     * @param message Message notified by {@link ObservableGameEnder}
     */
    void update(MessageToClient message);
}
