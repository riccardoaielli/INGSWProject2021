package it.polimi.ingsw.client;

import it.polimi.ingsw.client.LocalModel.LocalModel;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.messagesToClient.RankUpdate;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.RankPosition;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Interface implemented by GUI and CLI containing methods used to display changes and messages from controller and model
 */
public interface ClientView extends View {
    void start();
    void askCreateMatch();
    void askNickname();
    LocalModel getLocalModel();
    void showError(String errorString);
    void showInitialLeaderCardDiscard(String nickname, int indexLeaderCard1 , int indexLeaderCard2);
    void showUpdatedTemporaryMapResource(String nickname, Map<Resource, Integer> temporaryMapResource);
    void showUpdatedWarehouse(String nickname, List<Map<Resource, Integer>> depots);
    void showUpdatedStrongbox(String nickname, Map<Resource, Integer> strongbox);
    void showUpdatedDevCardSpace(String nickname, ArrayList<ArrayList<Integer>> cardsState);
    void showUpdateTemporaryMarbles(String nickname,Map<Marble,Integer> temporaryMarbles);
    void showUpdateRedcrossPosition(String nickname,int redcrossPosition);
    void showUpdatePopeFavourTiles(String nickname,ArrayList<Integer> popeFavourTiles);
    void showUpdatePlayerTurn(String nickname);
    void showUpdateMarket(Marble[][] marketMatrix,Marble marbleOut);
    void showUpdateAddSpecialDepotUpdate(String nickname, Resource depotResourceType);
    void showUpdateCardGridUpdate(int[][] cardGridMatrixUpdate);
    void showUpdateDiscardedLeaderUpdate(String nickname, int leaderPosition);
    void showUpdateLeaderCardActivatedUpdate(String nickname, int numLeadercard, int leaderCardID);
    void showUpdateRank(String nickname,ArrayList<RankPosition> rank);
}
