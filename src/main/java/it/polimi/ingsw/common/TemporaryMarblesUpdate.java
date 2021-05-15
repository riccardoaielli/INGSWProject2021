package it.polimi.ingsw.common;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.server.model.enumerations.Marble;

import java.util.HashMap;

public class TemporaryMarblesUpdate extends MessageToClient{
    HashMap<Marble,Integer> temporaryMarbles;
    public TemporaryMarblesUpdate(String nickname,HashMap<Marble,Integer> temporaryMarbles) {
        super(nickname, MessageType.TEMPORARY_MARBLES_UPDATE);
        this.temporaryMarbles = temporaryMarbles;
    }

    @Override
    public void handleMessage(ClientView clientView) {

    }
}
