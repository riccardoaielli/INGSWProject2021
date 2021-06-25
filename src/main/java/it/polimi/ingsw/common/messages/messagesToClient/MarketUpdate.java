package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.enumerations.Marble;

/**
 * Message to update the market of the client
 */
public class MarketUpdate extends MessageToClient {
    private final Marble[][] marketMatrix;
    private final Marble marbleOut;

    /**
     * Constructor of the message
     * @param marketMatrix the updated structure of the market
     * @param marbleOut the updated marble out
     */
    public MarketUpdate(Marble[][] marketMatrix,Marble marbleOut) {
        super(null, MessageType.MARKET_UPDATE);
        this.marketMatrix = marketMatrix;
        this.marbleOut = marbleOut;
    }

    /**
     * Shows the update and changes the local phase
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        if(clientView.getPhase() == LocalPhase.NICKNAME)
            clientView.setPhase(LocalPhase.LEADER_CHOICE);
        clientView.showUpdateMarket(marketMatrix,marbleOut);
    }
}
