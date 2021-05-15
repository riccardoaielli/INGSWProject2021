package it.polimi.ingsw.common.utils.observe;

import it.polimi.ingsw.common.messages.Message;

/**
 * A class can implement the MessageObserver interface when it wants to be informed of changes in MessageObservable objects
 */
public interface MessageObserver {
    /**
     * Receives a message from Observable
     * @param message Message notified by {@link it.polimi.ingsw.server.model.Observable}
     */
    void update(Message message);
}
