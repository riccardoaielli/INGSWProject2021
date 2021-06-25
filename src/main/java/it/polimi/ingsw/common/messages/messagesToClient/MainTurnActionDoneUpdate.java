package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

import java.util.HashMap;

/**
 * Message to update that a client that it's main turn action is complete
 */
public class MainTurnActionDoneUpdate extends MessageToClient{

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     */
    public MainTurnActionDoneUpdate(String nickname) {
        super(nickname, MessageType.MAIN_TURN_ACTION_DONE_UPDATE);
    }

    /**
     * Shows the update and changes the local phase
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        if(clientView.getNickname().equals(getNickname())) {
            clientView.setMainTurnActionDone(true);
            clientView.setPhase(LocalPhase.MENU);
            clientView.getPhase().handlePhase(clientView);
        }
    }
}
