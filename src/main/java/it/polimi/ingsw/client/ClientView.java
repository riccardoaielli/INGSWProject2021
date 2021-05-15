package it.polimi.ingsw.client;

import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Interface implemented by GUI and CLI containing methods used to display changes and messages from controller and model
 */
public interface ClientView extends View {
    void showError(String errorString);
    void showInitialLeaderCardDiscard(String nickname, int indexLeaderCard1 , int indexLeaderCard2);
    void showUpdatedTemporaryMapResource(String nickname, Map<Resource, Integer> temporaryMapResource);
    void showUpdatedWarehouse(String nickname, List<Map<Resource, Integer>> depots);
    void showUpdatedStrongbox(String nickname, Map<Resource, Integer> strongbox);
    void showUpdatedDevCardSpace(String nickname, ArrayList<ArrayList<Integer>> cardsState);
}
