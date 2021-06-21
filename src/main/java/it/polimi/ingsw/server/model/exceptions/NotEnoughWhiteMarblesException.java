package it.polimi.ingsw.server.model.exceptions;

/**
 * this exception is thrown when the leader card power is activated to transform marbles asking for an excessive quantity of marbles
 */
public class NotEnoughWhiteMarblesException extends Exception{

    /**
     * This method is thrown when the player ask to transform to more marbles than the amount of marbles taken from the market
     * @param message The reason why there are not enough marbles
     */
    public NotEnoughWhiteMarblesException(String message) {
        super(message);
    }
}
