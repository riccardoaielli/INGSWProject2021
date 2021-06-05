package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientSocket;
import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.GUI.Controller.InitialLeaderChoiceController;
import it.polimi.ingsw.client.LocalModel.LocalModel;
import it.polimi.ingsw.client.LocalModel.LocalPhase;
import it.polimi.ingsw.client.LocalSender;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.server.model.RankPosition;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUI implements ClientView {
    private LocalModel localModel;
    private MessageSender messageSender;
    private String myNickname;
    private LocalPhase localPhase;
    private String hostAddress;
    private int portNumber;
    private boolean mainTurnActionDone;

    public GUI(String hostAddress, int portNumber) {
        this.hostAddress = hostAddress;
        this.portNumber = portNumber;
        localPhase = LocalPhase.DEFAULT;
        mainTurnActionDone = false;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }


    public void setOnline(Boolean bool){
        if(bool){
            messageSender = new ClientSocket(hostAddress, portNumber, this);
            System.out.println("Online Mode");
        }
        else{
            messageSender = new LocalSender(this);
            System.out.println("Local Mode");
        }
    }

    @Override
    public void start() {
    }

    @Override
    public LocalModel getLocalModel() {
        return localModel;
    }

    @Override
    public void askCreateMatch() {
        SceneManager.getInstance().setRootFXML("firstConnection");
    }

    @Override
    public void askNickname() {
        SceneManager.getInstance().setRootFXML("nickname");
    }

    @Override
    public void askResourceChoice() {

    }

    @Override
    public void askAddToWareHouse() {

    }

    @Override
    public void askTurnAction() {

    }

    @Override
    public void askBuyDevCard() {

    }

    @Override
    public void askProduction() {

    }

    @Override
    public void askForLeaderPower() {

    }

    @Override
    public void askTakeFromMarketAction() {

    }

    @Override
    public void askRearrange() {

    }

    @Override
    public void showError(String errorString) {

    }

    @Override
    public void showInitialLeaderCardDiscard(String nickname, int indexLeaderCard1, int indexLeaderCard2) {

    }

    @Override
    public void showUpdatePlayersOrder(List<String> playersOrder) {

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
    public void showUpdateInitialLeaderCard(ArrayList<Integer> initialLeaderCardsID) {
        SceneManager.getInstance().setRootFXML("InitialLeaderChoice");
        InitialLeaderChoiceController controller = (InitialLeaderChoiceController) SceneManager.getInstance().getController("InitialLeaderChoice");
        controller.setLeaderCards(initialLeaderCardsID);
    }

    @Override
    public void showUpdateBlackcrossPosition(int blackCrossPosition) {

    }

    @Override
    public void showUpdateLorenzoDraw(String nickname, int row, int column) {

    }

    @Override
    public void askForLeaderCards() {
        InitialLeaderChoiceController controller = (InitialLeaderChoiceController) SceneManager.getInstance().getController("InitialLeaderChoice");
        controller.resetChoices();
    }

    @Override
    public void askDiscardLeader() {

    }

    @Override
    public void setPhase(LocalPhase phase) {
        this.localPhase = phase;
    }

    @Override
    public void setMainTurnActionDone(boolean mainTurnActionDone) {

    }

    @Override
    public LocalPhase getPhase() {
        return localPhase;
    }

    @Override
    public void showCurrentPlayer(String nickname) {

    }

    @Override
    public void askActivateLeader() {

    }

    @Override
    public void closeGame(String string) {

    }

    @Override
    public void update(MessageToClient message) {
        message.handleMessage(this);
    }

    @Override
    public String getNickname() {
        return myNickname;
    }

    public void setMyNickname(String myNickname) {
        this.myNickname = myNickname;
    }

    @Override
    public void setNickname(String nickname) {
        //TODO move nickname here and implement method
    }
}
