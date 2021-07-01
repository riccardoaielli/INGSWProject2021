package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

/**
 * Message to update the activation of the leader card to the client
 */
public class LeaderCardActivatedUpdate extends MessageToClient {
    private final int numLeaderCard;
    private final int leaderCardID;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param numLeaderCard the index of the activated leader card
     * @param leaderCardID the id of the updated card
     */
    public LeaderCardActivatedUpdate(String nickname, int numLeaderCard, int leaderCardID) {
        super(nickname, MessageType.LEADERCARD_ACTIVATED_UPDATE);
        this.numLeaderCard = numLeaderCard;
        this.leaderCardID = leaderCardID;
    }

    /**
     * Shows the update and changes the local phase
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {
        clientView.showUpdateLeaderCardActivatedUpdate(getNickname(), numLeaderCard,leaderCardID);
        if(getNickname().equals(clientView.getNickname())) {
            clientView.setPhase(LocalPhase.MENU);
            clientView.getPhase().handlePhase(clientView);
        }
    }
}
