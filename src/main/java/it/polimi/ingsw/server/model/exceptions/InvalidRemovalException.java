package it.polimi.ingsw.server.model.exceptions;

/**
 * This exception is thrown when trying to remove more resources than there are
 */
public class InvalidRemovalException extends Exception{
    /**
     * This method is thrown when a resource can't removed
     * @param message The reason why such resource can't be removed
     */
    public InvalidRemovalException(String message) {
        super(message);
    }
}
