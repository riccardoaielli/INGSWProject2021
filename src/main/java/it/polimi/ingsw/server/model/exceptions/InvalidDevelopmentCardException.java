package it.polimi.ingsw.server.model.exceptions;

public class InvalidDevelopmentCardException extends Exception{

    /**
     * This method is thrown when a selected development card is not valid
     * @param message The reason why such development card is not valid
     */
    public InvalidDevelopmentCardException(String message) {
        super(message);
    }
}
