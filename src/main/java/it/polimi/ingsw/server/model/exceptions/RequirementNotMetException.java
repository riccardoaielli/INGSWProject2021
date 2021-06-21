package it.polimi.ingsw.server.model.exceptions;

public class RequirementNotMetException extends Exception{

    /**
     * This method is thrown when the activation of a leader card fail because the player do not have enough resources or development card
     * @param message The reason why the requirement is not met
     */
    public RequirementNotMetException(String message) {
        super(message);
    }
}
