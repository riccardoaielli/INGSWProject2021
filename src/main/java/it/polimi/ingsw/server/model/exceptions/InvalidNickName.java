package it.polimi.ingsw.server.model.exceptions;

public class InvalidNickName extends Exception{

    /**
     * This method is thrown when a nickname is not valid
     * @param message The reason why such is not valid
     */
    public InvalidNickName(String message) {
        super(message);
    }
}
