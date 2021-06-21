package it.polimi.ingsw.server.model.exceptions;

public class NoCardException extends Exception{

    /**
     * This method is thrown when a card in the card grid is not available
     * @param message The reason why the card is not available
     */
    public NoCardException(String message) {
        super(message);
    }
}
