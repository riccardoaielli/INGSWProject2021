package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

public class MainTurnActionDoneUpdate extends MessageToClient{
    public MainTurnActionDoneUpdate(String nickname) {
        super(nickname, MessageType.MAIN_TURN_ACTION_DONE_UPDATE);
    }

    @Override
    public void handleMessage(ClientView clientView) {
        clientView.setMainTurnActionDone(true);
        clientView.setPhase(LocalPhase.MENU);
        clientView.getPhase().handlePhase(clientView);
    }
}
