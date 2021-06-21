package it.polimi.ingsw.server.model.exceptions;

public class InvalidParameterException extends Exception{

    /**
     * This method is thrown when a given parameter is not valid
     * @param message The reason why such parameter is not valid
     */
    public InvalidParameterException(String message) {
        super(message);
    }
}
