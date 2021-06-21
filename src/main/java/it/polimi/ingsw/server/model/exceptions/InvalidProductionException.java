package it.polimi.ingsw.server.model.exceptions;

public class InvalidProductionException extends Exception{

    /**
     * This method is thrown when a production can't be activated
     * @param message The reason why such production can't be activated
     */
    public InvalidProductionException(String message) {
        super(message);
    }
}
