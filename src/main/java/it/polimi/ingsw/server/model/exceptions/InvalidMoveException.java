package it.polimi.ingsw.server.model.exceptions;

public class InvalidMoveException extends Exception{

    /**
     * This method is thrown when a move between depots can't be done
     * @param message The reason why such move can't be done
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
