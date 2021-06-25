package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;

/**
 * This class represents the message sent from the client to transform the white marbles obtained from the market with a leader ability
 */
public class TransformWhiteMarblesMessage extends MessageToServer {
    private final int leaderCard;
    private final int numOfTransformations;

    /**
     * Constructor of the message
     * @param nickname the player that sent the message
     * @param leaderCard the index of the leader card to use to transform th emarbles
     * @param numOfTransformations the number of marbles to transform
     */
    public TransformWhiteMarblesMessage(String nickname, int leaderCard,int numOfTransformations) {
        super(nickname, MessageType.TRANSFORM_WHITE_MARBLES);
        this.leaderCard = leaderCard;
        this.numOfTransformations = numOfTransformations;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) { controller.handleTransformWhiteMarblesMessage(leaderCard,numOfTransformations,this.getNickname(), view); }
}
