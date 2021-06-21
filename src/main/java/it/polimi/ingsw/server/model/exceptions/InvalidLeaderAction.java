package it.polimi.ingsw.server.model.exceptions;

public class InvalidLeaderAction extends Exception{

    /**
     * This method is thrown when a Leader power can't be used
     * @param message The reason why such leader power can't be used
     */
    public InvalidLeaderAction(String message) {
        super(message);
    }
}
