package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.CLI;
import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

public class PlayerTurnUpdate extends MessageToClient {
    public PlayerTurnUpdate(String nickname) {
        super(nickname, MessageType.PLAYER_TURN_UPDATE);
    }

    @Override
    public void handleMessage(ClientView clientView) {
        if(clientView.getLocalModel().getLocalPlayer().getNickname().equals(getNickname())){
            if(clientView.getPhase() == LocalPhase.LEADER_CHOICE)
                clientView.setPhase(LocalPhase.RESOURCE_CHOICE);
            else if (clientView.getPhase() == LocalPhase.RESOURCE_CHOICE){
                //TODO passare a standard round
            }
            clientView.showUpdatePlayerTurn(getNickname());
        }
    }
}
