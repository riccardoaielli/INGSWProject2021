package it.polimi.ingsw.server.model.exceptions;

public class InvalidCostException extends Exception {

    /**
     * This method is thrown when a cost is not valid
     * @param message The reason why such cost is not valid
     */
    public InvalidCostException(String message) {
        super(message);
    }

}
