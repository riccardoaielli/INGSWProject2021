package it.polimi.ingsw.server.model;

/**
 * interface for classes that can verify the condition to end the game
 * @param <T> the class that implements EndGameConditionObserver
 */
public interface ObservableGameEnder<T>{
    void addObserver(T observer);
}
