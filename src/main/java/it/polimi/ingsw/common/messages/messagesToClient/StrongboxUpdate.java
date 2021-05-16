package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

public class StrongboxUpdate extends MessageToClient {
    private final Map<Resource, Integer> strongbox;
    public StrongboxUpdate(String nickname, Map<Resource, Integer> strongbox) {
        super(nickname, MessageType.STRONGBOX_UPDATE);
        this.strongbox = strongbox;
    }

    @Override
    public void handleMessage(ClientView clientView) { clientView.showUpdatedStrongbox(this.getNickname(), strongbox);
    }
}
