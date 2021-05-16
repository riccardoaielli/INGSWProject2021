package it.polimi.ingsw.common.utils.observe;

import it.polimi.ingsw.common.messages.Message;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used to notify listeners registered through {@link MessageObservable} interface
 */
public class MessageObservable {
    private List<MessageObserver> messageObservers = new ArrayList<>();
    private String nickname;

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
    public void notifyObservers(MessageToClient message){
        for (MessageObserver messageObserver : messageObservers){
            messageObserver.update(message);
        }
    }

    /**
     * Method to add the nickname of the player that owns the observed object in the game
     * @param nickname nickname of the player whose object is observed, if null the object is shared by all players
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Method that return the nickname of the player that owns the observed object in the game
     * @return nickname of the player whose object is observed, if null the object is shared by all players
     */
    public String getNickname() {
        return nickname;
    }

}
