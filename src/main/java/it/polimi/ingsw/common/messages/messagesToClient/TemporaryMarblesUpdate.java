package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.enumerations.Marble;

import java.util.Map;

public class TemporaryMarblesUpdate extends MessageToClient {
    private final Map<Marble,Integer> temporaryMarbles;
    public TemporaryMarblesUpdate(String nickname,Map<Marble,Integer> temporaryMarbles) {
        super(nickname, MessageType.TEMPORARY_MARBLES_UPDATE);
        this.temporaryMarbles = temporaryMarbles;
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateTemporaryMarbles(getNickname(),temporaryMarbles);
        if(clientView.getNickname().equals(getNickname())) {
            clientView.setPhase(LocalPhase.TEMPORARY_MARBLES);
            clientView.getPhase().handlePhase(clientView);
        }
    }
}
