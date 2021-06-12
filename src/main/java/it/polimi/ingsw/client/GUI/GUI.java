package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientSocket;
import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.GUI.Controller.*;
import it.polimi.ingsw.client.CLI.LocalModel.LocalModel;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.client.LocalSender;
import it.polimi.ingsw.client.MessageSender;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;
import it.polimi.ingsw.server.model.RankPosition;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Collections;
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
        SceneManager.getInstance().showPopup("initialResourceChoice");
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
        SceneManager.getInstance().showError(errorString);
    }

    @Override
    public void showInitialLeaderCardDiscard(String nickname, int indexLeaderCard1, int indexLeaderCard2) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController)SceneManager.getInstance().getController("gameInterface");
        PersonalBoardController myPersonalBoard =  gameInterfaceController.getPersonalBoardControllerMap().get(nickname);

        if (nickname.equals(myNickname)){
            InitialLeaderChoiceController initialLeaderChoiceController = (InitialLeaderChoiceController) SceneManager.getInstance().getController("initialLeaderChoice");

            List<Integer> indexesLeaderCard = new ArrayList<>();
            indexesLeaderCard.add(indexLeaderCard1);
            indexesLeaderCard.add(indexLeaderCard2);
            indexesLeaderCard.sort(Collections.reverseOrder());
            for (int indexLeaderCard: indexesLeaderCard){
                initialLeaderChoiceController.getCardImagesArray().remove(indexLeaderCard-1);
            }
        }
    }

    @Override
    public void showUpdatePlayersOrder(List<String> playersOrder) {
        SceneManager.getInstance().setRootFXML("gameInterface");
        GameInterfaceController gameInterfaceController = (GameInterfaceController)SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> {
            //Creating personal boards of the players
            gameInterfaceController.setPlayers(playersOrder);
            //Setting the chosen leader cards of the player of this client
            InitialLeaderChoiceController initialLeaderChoiceController = (InitialLeaderChoiceController) SceneManager.getInstance().getController("initialLeaderChoice");
            PersonalBoardController myPersonalBoard =  gameInterfaceController.getPersonalBoardControllerMap().get(myNickname);
            myPersonalBoard.setLeaderCard1(initialLeaderChoiceController.getCardImagesArray().get(0).getImage());
            myPersonalBoard.setLeaderCard2(initialLeaderChoiceController.getCardImagesArray().get(1).getImage());
        });

    }

    @Override
    public void showUpdatedTemporaryMapResource(String nickname, Map<Resource, Integer> temporaryMapResource) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> {
            PersonalBoardController personalBoardController = gameInterfaceController.getPersonalBoardControllerMap().get(nickname);
            personalBoardController.updateTemporaryResourceMap(temporaryMapResource);
        });
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
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.setMarbles(marketMatrix, marbleOut));
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
        //TODO evaluate if necessary
    }

    @Override
    public void showUpdateInitialLeaderCard(ArrayList<Integer> initialLeaderCardsID) {
        SceneManager.getInstance().setRootFXML("initialLeaderChoice");
        InitialLeaderChoiceController controller = (InitialLeaderChoiceController) SceneManager.getInstance().getController("initialLeaderChoice");
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
        InitialLeaderChoiceController controller = (InitialLeaderChoiceController) SceneManager.getInstance().getController("initialLeaderChoice");
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
