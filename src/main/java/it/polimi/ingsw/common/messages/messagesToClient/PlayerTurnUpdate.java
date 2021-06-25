package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message to update the player that is currently playing the turn
 */
public class PlayerTurnUpdate extends MessageToClient {
    /**
     * Constructor of the message
     * @param nickname the player that has to play its turn
     */
    public PlayerTurnUpdate(String nickname) {
        super(nickname, MessageType.PLAYER_TURN_UPDATE);
    }

    /**
     * Shows the update and changes the local phase
     * @param clientView the view to update
     */
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
