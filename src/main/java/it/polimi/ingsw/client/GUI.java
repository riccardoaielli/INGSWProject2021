package it.polimi.ingsw.client;

import it.polimi.ingsw.client.LocalModel.LocalModel;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.server.model.RankPosition;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUI implements ClientView{
    LocalModel localModel;
    private void setMessageSender(){

    }

    @Override
    public void start() {

    }

    @Override
    public LocalModel getLocalModel() {
        return localModel;
    }

    @Override
    public void showError(String errorString) {

    }

    @Override
    public void showInitialLeaderCardDiscard(String nickname, int indexLeaderCard1, int indexLeaderCard2) {

    }

    @Override
    public void showUpdatedTemporaryMapResource(String nickname, Map<Resource, Integer> temporaryMapResource) {

    }

    @Override
    public void showUpdatedWarehouse(String nickname, List<Map<Resource, Integer>> depots) {

    }

    @Override
    public void showUpdatedStrongbox(String nickname, Map<Resource, Integer> strongbox) {

    }

    @Override
    public void showUpdatedDevCardSpace(String nickname, ArrayList<ArrayList<Integer>> cardsState) {

    }

    @Override
    public void showUpdateTemporaryMarbles(String nickname, Map<Marble, Integer> temporaryMarbles) {

    }

    @Override
    public void showUpdateRedcrossPosition(String nickname, int redcrossPosition) {

    }

    @Override
    public void showUpdatePopeFavourTiles(String nickname, ArrayList<Integer> popeFavourTiles) {

    }

    @Override
    public void showUpdatePlayerTurn(String nickname) {

    }

    @Override
    public void showUpdateMarket(Marble[][] marketMatrix, Marble marbleOut) {

    }

    @Override
    public void showUpdateAddSpecialDepotUpdate(String nickname, Resource depotResourceType) {

    }

    @Override
    public void showUpdateCardGridUpdate(int[][] cardGridMatrixUpdate) {

    }

    @Override
    public void showUpdateDiscardedLeaderUpdate(String nickname, int leaderPosition) {

    }

    @Override
    public void showUpdateLeaderCardActivatedUpdate(String nickname, int numLeadercard, int leaderCardID) {

    }

    @Override
    public void showUpdateRank(String nickname, ArrayList<RankPosition> rank) {

    }

    @Override
    public void showUpdateFirstConnection(boolean firstPlayer) {

    }

    @Override
    public void update(MessageToClient message) {

    }
}
