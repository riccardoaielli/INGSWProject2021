package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public class TransformWhiteMarblesMessage extends MessageToServer{
    private int leaderCard;
    private int numOfTransformations;

    public TransformWhiteMarblesMessage(String nickname, MessageType messageType, int leaderCard,int numOfTransformations) {
        super(nickname, messageType);
        this.leaderCard = leaderCard;
        this.numOfTransformations = numOfTransformations;
    }

    @Override
    public void handleMessage(Controller controller, View virtualView) { controller.handleTransformWhiteMarblesMessage(leaderCard,numOfTransformations,this.getNickname(),virtualView); }
}
