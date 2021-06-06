package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

public class PlayerTurnUpdate extends MessageToClient {
    public PlayerTurnUpdate(String nickname) {
        super(nickname, MessageType.PLAYER_TURN_UPDATE);
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdatePlayerTurn(getNickname());
        if(clientView.getNickname().equals(getNickname())){
            if(clientView.getPhase() == LocalPhase.LEADER_CHOICE)
                clientView.setPhase(LocalPhase.RESOURCE_CHOICE);
            else
                clientView.setPhase(LocalPhase.MENU);
            clientView.setMainTurnActionDone(false);
            clientView.getPhase().handlePhase(clientView);
        }
    }
}
