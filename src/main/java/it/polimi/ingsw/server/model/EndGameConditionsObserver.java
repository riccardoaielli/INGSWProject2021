package it.polimi.ingsw.server.model;

/**
 * Interface for the observer that observe the classes that can verify the conditions to end the game
 */
public interface EndGameConditionsObserver {
    void update(boolean lorenzo);
}
