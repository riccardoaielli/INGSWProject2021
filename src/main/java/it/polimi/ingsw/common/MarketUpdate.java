package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.server.model.enumerations.Marble;

public class MarketUpdate extends MessageToClient{
    private Marble[][] marketMatrix;
    private Marble marbleOut;

    public MarketUpdate(Marble[][] marketMatrix,Marble marbleOut) {
        super(null, MessageType.MARKET_UPDATE);
        this.marketMatrix = marketMatrix;
        this.marbleOut = marbleOut;
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }
}
