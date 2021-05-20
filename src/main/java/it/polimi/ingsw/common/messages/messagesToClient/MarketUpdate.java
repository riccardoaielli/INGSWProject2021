package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.enumerations.Marble;

public class MarketUpdate extends MessageToClient {
    private final Marble[][] marketMatrix;
    private final Marble marbleOut;

    public MarketUpdate(Marble[][] marketMatrix,Marble marbleOut) {
        super(null, MessageType.MARKET_UPDATE);
        this.marketMatrix = marketMatrix;
        this.marbleOut = marbleOut;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.getLocalModel().setPhase(LocalPhase.LEADER_CHOICE);
        clientView.showUpdateMarket(marketMatrix,marbleOut);
    }
}
