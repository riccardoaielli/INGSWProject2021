package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

/**
 * This class represents the message sent from the client to transform the white marbles obtained from the market with a leader ability
 */
public class TransformWhiteMarblesMessage extends MessageToServer{
    private int leaderCard;
    private int numOfTransformations;

    public TransformWhiteMarblesMessage(String nickname, int leaderCard,int numOfTransformations) {
        super(nickname, MessageType.TRANSFORM_WHITE_MARBLES);
        this.leaderCard = leaderCard;
        this.numOfTransformations = numOfTransformations;
    }

    @Override
    public void handleMessage(Controller controller, View virtualView) { controller.handleTransformWhiteMarblesMessage(leaderCard,numOfTransformations,this.getNickname(),virtualView); }
}
