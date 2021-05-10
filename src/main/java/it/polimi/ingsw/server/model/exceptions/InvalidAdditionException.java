package it.polimi.ingsw.server.model.exceptions;

public class InvalidAdditionException extends Exception{

    /**
     * This method is thrown when a resource can't be added to WarehouseDepots
     * @param message The reason why such resource can't be added
     */
    public InvalidAdditionException(String message) {
        super(message);
    }
}
