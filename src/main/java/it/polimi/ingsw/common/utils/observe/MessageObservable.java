package it.polimi.ingsw.common.utils.observe;

import it.polimi.ingsw.common.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used to notify listeners registered through {@link MessageObservable} interface
 */
public class MessageObservable {
    private List<MessageObserver> messageObservers = new ArrayList<>();

    /**
     * Adds an observer to the list of observer
     * @param messageObserver Observer to register
     */
    public void addObserver(MessageObserver messageObserver){
        messageObservers.add(messageObserver);
    }

    public void addObserverList(List<MessageObserver> messageObservers){ this.messageObservers = messageObservers; }
    /**
     * Used to get the list of the observers
     * @return the list of observers
     */
    public List<MessageObserver> getMessageObservers() {
        return messageObservers;
    }

    /**
     * Remove an observer to the list of observer
     * @param messageObserver Observer to remove
     */
    public void removeObserver(MessageObserver messageObserver){
        messageObservers.remove(messageObserver);
    }

    /**
     * Notifies all observers with a message
     * @param message Message notified to all observers
     */
    public void notifyObservers(Message message){
        for (MessageObserver messageObserver : messageObservers){
            messageObserver.update(message);
        }
    }

}
