package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;

import java.util.ArrayList;

public class InitialLeaderCardsUpdate extends MessageToClient{
    ArrayList<Integer> initialLeaderCardsID;
    public InitialLeaderCardsUpdate(String nickname, ArrayList<Integer> initialLeaderCardsID) {
        super(nickname, MessageType.INITIAL_LEADERCARDS_UPDATE);
        this.initialLeaderCardsID = initialLeaderCardsID;
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }
}
