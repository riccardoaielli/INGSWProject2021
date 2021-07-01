package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientSocket;
import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.GUI.Controller.*;
import it.polimi.ingsw.client.CLI.LocalModel.LocalModel;
import it.polimi.ingsw.client.LocalPhase;
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
    private boolean firstTurn;
    private boolean firstProductionDone;

    public GUI(String hostAddress, int portNumber) {
        this.hostAddress = hostAddress;
        this.portNumber = portNumber;
        localPhase = LocalPhase.DEFAULT;
        mainTurnActionDone = false;
        firstTurn = true;
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
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.initialResourceChoiceButtonDisabled());
    }

    @Override
    public void askAddToWareHouse() {
        SceneManager.getInstance().showPopup("addToWarehouse");
    }

    @Override
    public void askTurnAction() {
        if(firstTurn)
            firstTurn = false;
    }

    @Override
    public void askBuyDevCard() {
        SceneManager.getInstance().showPopup("buyCardInterface");
    }

    @Override
    public void askProduction() {
        SceneManager.getInstance().showPopup("production");
    }

    @Override
    public void setFirstProductionDone(boolean firstProductionDone) {
        this.firstProductionDone = firstProductionDone;
    }

    @Override
    public void askForLeaderPower() {
        SceneManager.getInstance().showPopup("whiteMarbleLeaderPower");
    }

    @Override
    public void askTakeFromMarketAction() {
        SceneManager.getInstance().showPopup("takeFromMarket");
    }

    @Override
    public void askRearrange() {
        SceneManager.getInstance().showPopup("rearrangeWHouseInterface");
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
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.setWarehouse(nickname, depots));
    }

    @Override
    public void showUpdatedStrongbox(String nickname, Map<Resource, Integer> strongbox) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.setStrongbox(nickname, strongbox));
    }

    @Override
    public void showUpdatedDevCardSpace(String nickname, ArrayList<ArrayList<Integer>> cardsState) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.setDevelopmentCardSpace(nickname, cardsState));
    }

    @Override
    public void showUpdateTemporaryMarbles(String nickname, Map<Marble, Integer> temporaryMarbles) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.setTemporaryMarblesMap(nickname, temporaryMarbles));
    }

    @Override
    public void showUpdateRedcrossPosition(String nickname, int redcrossPosition) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.setRedCrossPosition(nickname, redcrossPosition));
    }

    @Override
    public void showUpdatePopeFavourTiles(String nickname, ArrayList<Integer> popeFavourTiles) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.setPopeFavourTiles(nickname, popeFavourTiles));
    }

    @Override
    public void showUpdatePlayerTurn(String nickname) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.updatePlayerTurn(nickname));
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
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.setCardGrid(cardGridMatrixUpdate));
    }

    @Override
    public void showUpdateDiscardedLeaderUpdate(String nickname, int leaderPosition) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.setDiscardLeader(nickname, leaderPosition));
    }

    @Override
    public void showUpdateLeaderCardActivatedUpdate(String nickname, int leaderPosition, int leaderCardID) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.setActivateLeader(nickname, leaderPosition, leaderCardID));
    }

    @Override
    public void showUpdateRank(String nickname, ArrayList<RankPosition> rank) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.updateRank(rank));
    }

    @Override
    public void showUpdateInitialLeaderCard(ArrayList<Integer> initialLeaderCardsID) {
        SceneManager.getInstance().setRootFXML("initialLeaderChoice");
        InitialLeaderChoiceController controller = (InitialLeaderChoiceController) SceneManager.getInstance().getController("initialLeaderChoice");
        controller.setLeaderCards(initialLeaderCardsID);
    }

    @Override
    public void showUpdateBlackcrossPosition(int blackCrossPosition) {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        Platform.runLater(()-> gameInterfaceController.setBlackCrossPosition(blackCrossPosition));
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
        SceneManager.getInstance().showPopup("discardLeaderInterface");
    }

    @Override
    public void setPhase(LocalPhase phase) {
        System.out.println("Local phase set to: " + phase);
        this.localPhase = phase;
    }

    @Override
    public void setMainTurnActionDone(boolean mainTurnActionDone) {
        this.mainTurnActionDone = mainTurnActionDone;
        firstProductionDone = false;
        if(mainTurnActionDone){
            GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
            Platform.runLater(()-> gameInterfaceController.updateMainTurnAction());
        }

    }

    @Override
    public LocalPhase getPhase() {
        return localPhase;
    }

    @Override
    public void askActivateLeader() {
        SceneManager.getInstance().showPopup("activateLeaderInterface");
    }

    @Override
    public void closeGame(String string) {
        SceneManager.getInstance().showClientDisconnectedInterface(string);
        System.out.println(string);
    }
    @Override
    public void update(MessageToClient message) {
        message.handleMessage(this);
    }

    @Override
    public String getNickname() {
        return myNickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.myNickname = myNickname;
    }

    public void exitGame() {
        System.exit(0);
    }

    public boolean isFirstTurn() {
        return firstTurn;
    }

    public boolean isFirstProductionDone() {
        return firstProductionDone;
    }
}
