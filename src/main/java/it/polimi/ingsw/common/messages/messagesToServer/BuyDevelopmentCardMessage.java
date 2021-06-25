package it.polimi.ingsw.common.messages.messagesToServer;

import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Message sent from the client to buy a development card from the card grid
 */
public class BuyDevelopmentCardMessage extends MessageToServer {

    private final int row;
    private final int column;
    private final Map<Resource, Integer> costStrongbox;
    private final Map<Resource, Integer> costWarehouse;
    private final int numLeaderCard;
    private final int cardPosition;

    /**
     * Constructor of the message
     * @param nickname nickname of the player that sends the message
     * @param row a row of the development card space
     * @param column a column of the development card space
     * @param costStrongbox a map of resources to get from the strongbox to buy the card
     * @param costWarehouse a map of resources to get from the strongbox to buy the card
     * @param numLeaderCard the index of the leader card to use to discount the price of the card
     * @param cardPosition a position in the development card space for the new card
     */
    public BuyDevelopmentCardMessage(String nickname, int row, int column, Map<Resource, Integer> costStrongbox, Map<Resource, Integer> costWarehouse, int numLeaderCard, int cardPosition) {
        super(nickname, MessageType.BUY_DEVELOPMENT_CARD);
        this.row = row;
        this.column = column;
        this.costStrongbox = costStrongbox;
        this.costWarehouse = costWarehouse;
        this.numLeaderCard = numLeaderCard;
        this.cardPosition = cardPosition;
    }

    /**
     * Interacts with the corresponding method of the controller
     * @param controller the controller that has to handle the message
     * @param view the view that sent the message
     */
    @Override
    public void handleMessage(Controller controller, View view) {
        controller.handleBuyDevelopmentCardMessage(view, this.getNickname(), row, column, costStrongbox, costWarehouse, numLeaderCard, cardPosition);
    }
}
