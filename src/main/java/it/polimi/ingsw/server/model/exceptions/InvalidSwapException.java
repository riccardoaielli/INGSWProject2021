package it.polimi.ingsw.server.model.exceptions;

public class InvalidSwapException extends Exception{

    /**
     * This method is thrown when the swap asked from the player is not valid
     * @param message The reason why the swap is not valid
     */
    public InvalidSwapException(String message) {
        super(message);
    }
}
