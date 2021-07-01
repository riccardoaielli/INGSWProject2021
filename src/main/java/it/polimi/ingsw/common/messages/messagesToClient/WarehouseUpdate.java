package it.polimi.ingsw.common.messages.messagesToClient;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.LocalPhase;
import it.polimi.ingsw.common.messages.MessageType;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.List;
import java.util.Map;

/**
 * Message to update the warehouse of the client
 */
public class WarehouseUpdate extends MessageToClient {
    private final List<Map<Resource, Integer>> depots;

    /**
     * Constructor of the message
     * @param nickname the player that gets updated
     * @param depots the update structure of the warehouse
     */
    public WarehouseUpdate(String nickname, List<Map<Resource, Integer>> depots) {
        super(nickname, MessageType.WAREHOUSE_UPDATE);
        this.depots = depots;
    }

    /**
     * Shows the update and changes the local phase
     * @param clientView the view to update
     */
    @Override
    public void handleMessage(ClientView clientView) {

        clientView.showUpdatedWarehouse(this.getNickname(), depots);
        if(clientView.getNickname().equals(getNickname())){
            //Return to main menu after rearranging
            if(clientView.getPhase() == LocalPhase.REARRANGE_WAREHOUSE){
                clientView.setPhase(LocalPhase.ADD_TO_WAREHOUSE);
                clientView.getPhase().handlePhase(clientView);
            }
        }
    }
}
