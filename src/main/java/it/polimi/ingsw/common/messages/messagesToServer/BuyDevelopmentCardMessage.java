package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.HashMap;
import java.util.Map;

public class BuyDevelopmentCardMessage extends MessageToServer {

    private final int row;
    private final int column;
    private final Map<Resource, Integer> costStrongbox;
    private final Map<Resource, Integer> costWarehouse;
    private final int numLeaderCard;
    private final int cardPosition;

    public BuyDevelopmentCardMessage(String nickname, int row, int column, Map<Resource, Integer> costStrongbox, Map<Resource, Integer> costWarehouse, int numLeaderCard, int cardPosition) {
        super(nickname, MessageType.BUY_DEVELOPMENT_CARD);
        this.row = row;
        this.column = column;
        this.costStrongbox = costStrongbox;
        this.costWarehouse = costWarehouse;
        this.numLeaderCard = numLeaderCard;
        this.cardPosition = cardPosition;
    }

    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleBuyDevelopmentCardMessage(view, this.getNickname(), row, column, costStrongbox, costWarehouse, numLeaderCard, cardPosition);
    }
}
