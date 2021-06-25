package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;

import java.util.List;

/**
 * Message to update the order of the players at the beginning of the match
 */
public class PlayersOrderUpdate extends MessageToClient{
    List<String> playerNicknames;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param playerNicknames an ordered list of players
     */
    public PlayersOrderUpdate(String nickname, List<String> playerNicknames) {
        super(nickname, MessageType.PLAYERS_ORDER_UPDATE);
        this.playerNicknames = playerNicknames;
    }

    /**
     * Shows the update and changes the local phase
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {

        clientView.showUpdatePlayersOrder(playerNicknames);
        //TODO in playerTurnUpdate would be better
        if (playerNicknames.indexOf(clientView.getNickname())== 0){
            clientView.setPhase(LocalPhase.MENU);
        }

    }
}
