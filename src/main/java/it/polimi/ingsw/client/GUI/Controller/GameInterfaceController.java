package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.EndTurnMessage;
import it.polimi.ingsw.common.messages.messagesToServer.TakeFromMarketMessage;
import it.polimi.ingsw.server.model.RankPosition;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInterfaceController extends AbstractController {

    @FXML
    private Button marketButton;
    @FXML
    private Button buyCardButton;
    @FXML
    private Button productionButton;
    @FXML
    private Button activateLeaderButton;
    @FXML
    private Button discardLeaderButton;
    @FXML
    private Button rearrangeWHouseButton;
    @FXML
    private Button endTurnButton;
    @FXML
    private Button quitButton;

    //CARD GRID
    @FXML
    private CardGridController cardGridController;

    //PERSONAL BOARDS
    @FXML
    private TabPane personalTab;
    private Map<String, PersonalBoardController> personalBoardControllerMap;

    //MARKET
    @FXML
    private MarketController marketGridController;

    @FXML
    public void initialize() {
        personalBoardControllerMap = new HashMap<>();
        marketButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMarketButtonClick);
        buyCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBuyCardButtonClick);
        productionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onProductionButtonClick);
        activateLeaderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onActivateLeaderButtonClick);
        discardLeaderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDiscardLeaderButtonClick);
        rearrangeWHouseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onRearrangeWHouseButtonClick);
        endTurnButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onEndTurnButtonClick);
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onQuitButtonClick);
    }

    private void onMarketButtonClick(Event event){
        getGui().setPhase(LocalPhase.TAKE_FROM_MARKET);
        getGui().getPhase().handlePhase(getGui());
    }

    private void onBuyCardButtonClick(Event event){
        getGui().setPhase(LocalPhase.BUY_DEV_CARD);
        getGui().getPhase().handlePhase(getGui());
    }

    private void onProductionButtonClick(Event event){
        getGui().setPhase(LocalPhase.ACTIVATE_PRODUCTION);
        getGui().getPhase().handlePhase(getGui());
    }

    private void onActivateLeaderButtonClick(Event event){
        getGui().setPhase(LocalPhase.ACTIVATE_LEADER);
        getGui().getPhase().handlePhase(getGui());
    }

    private void onDiscardLeaderButtonClick(Event event){
        getGui().setPhase(LocalPhase.DISCARD_LEADER);
        getGui().getPhase().handlePhase(getGui());
    }

    private void onRearrangeWHouseButtonClick(Event event){
        //It can be done while doing other actions, phase is not changed
        getGui().askRearrange();
    }

    private void onEndTurnButtonClick(Event event){
        getGui().getMessageSender().sendMessage(new EndTurnMessage(getGui().getNickname()));
    }

    private void onQuitButtonClick(Event event){
        SceneManager.getInstance().showPopup("quitInterface");
    }


    /**
     * Method to set the tabs of all the players in the GUI
     * @param playersOrder list containing the nickname of players ordered by the order in game
     */
    public void setPlayers(List<String> playersOrder){
        for (String nickname : playersOrder) {
            FXMLLoader tabContent = SceneManager.getInstance().loadFXML("personalBoard");
            try {
                Tab playerTab = new Tab(nickname, tabContent.load());
                personalBoardControllerMap.put(nickname, tabContent.getController());
                personalTab.getTabs().add(playerTab);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(personalBoardControllerMap.size() == (1)){
            PersonalBoardController personalBoardController = personalBoardControllerMap.get(getGui().getNickname());
            personalBoardController.initializeBlackCross();
        }
    }

    public void setWarehouse(String nickname, List<Map<Resource, Integer>> depots){
        PersonalBoardController personalBoardController = personalBoardControllerMap.get(nickname);
        personalBoardController.updateWarehouse(depots);
    }

    public void setStrongbox(String nickname, Map<Resource, Integer> strongbox){
        PersonalBoardController personalBoardController = personalBoardControllerMap.get(nickname);
        personalBoardController.updateStrongbox(strongbox);
    }

    public void setTemporaryMarblesMap(String nickname, Map<Marble,Integer> temporaryMarbles){
        PersonalBoardController personalBoardController = personalBoardControllerMap.get(nickname);
        personalBoardController.updateTemporaryMarbleMap(temporaryMarbles);
    }

    public void setDiscardLeader(String nickname, int numLeaderCard){
        PersonalBoardController personalBoardController = personalBoardControllerMap.get(nickname);
        personalBoardController.updateDiscardedLeader(numLeaderCard);
    }

    public void setActivateLeader(String nickname, int numLeaderCard, int leaderCardID){
        //nickname = SceneManager.getInstance().getGui().getNickname();  serve solo se voglio lavorare sulla mia personal board, non è questo il caso
        PersonalBoardController personalBoardController = personalBoardControllerMap.get(nickname);
        personalBoardController.updateActivatedLeader(numLeaderCard, leaderCardID);
    }

    public void setRedCrossPosition(String nickname, int redcrossPosition){
        PersonalBoardController personalBoardController = personalBoardControllerMap.get(nickname);
        personalBoardController.setRedCrossPosition(redcrossPosition);
    }

    public void setBlackCrossPosition(int blackCrossPosition){
        PersonalBoardController personalBoardController = personalBoardControllerMap.get(getGui().getNickname());
        personalBoardController.setBlackCrossPosition(blackCrossPosition);
    }

    public void setPopeFavourTiles(String nickname, ArrayList<Integer> popeFavourTiles){
        PersonalBoardController personalBoardController = personalBoardControllerMap.get(nickname);
        personalBoardController.setPopeFavourTiles(popeFavourTiles);
    }

    public void setDevelopmentCardSpace(String nickname, ArrayList<ArrayList<Integer>> cardsState){
        PersonalBoardController personalBoardController = personalBoardControllerMap.get(nickname);
        personalBoardController.setDevelopmentCardSpace(cardsState);
    }

    public void updateMainTurnAction() {
        marketButton.setDisable(true);
        buyCardButton.setDisable(true);
        productionButton.setDisable(true);
    }

    public void initialResourceChoiceButtonDisabled() {
        rearrangeWHouseButton.setDisable(true);
        marketButton.setDisable(true);
        buyCardButton.setDisable(true);
        productionButton.setDisable(true);
        activateLeaderButton.setDisable(true);
        discardLeaderButton.setDisable(true);
        endTurnButton.setDisable(true);
    }

    public void updatePlayerTurn(String nickname){

        if(nickname.equals(getGui().getNickname()))
        {
            rearrangeWHouseButton.setDisable(false);
            marketButton.setDisable(false);
            buyCardButton.setDisable(false);
            productionButton.setDisable(false);
            activateLeaderButton.setDisable(false);
            discardLeaderButton.setDisable(false);
            endTurnButton.setDisable(false);
        }else{
            rearrangeWHouseButton.setDisable(true);
            marketButton.setDisable(true);
            buyCardButton.setDisable(true);
            productionButton.setDisable(true);
            activateLeaderButton.setDisable(true);
            discardLeaderButton.setDisable(true);
            endTurnButton.setDisable(true);
        }

        ObservableList<Tab> list = personalTab.getTabs();
        for(Tab tab : list){
            tab.setStyle("-fx-background-color: white");
            if(tab.getText().equals(nickname)){
                tab.setStyle("-fx-background-color: #34eb61"); //#34eb61 colore verde chiaro
            }
        }
    }

    public void updateRank(ArrayList<RankPosition> rank){
        String string = "";
        for(RankPosition rankPosition : rank){
            string = string + rankPosition.toString() + "       ";
        }
        SceneManager.getInstance().showClientDisconnectedInterface(string);
        rearrangeWHouseButton.setDisable(true);
        marketButton.setDisable(true);
        buyCardButton.setDisable(true);
        productionButton.setDisable(true);
        activateLeaderButton.setDisable(true);
        discardLeaderButton.setDisable(true);
        endTurnButton.setDisable(true);
        /*SceneManager.getInstance().setRootFXML("rankInterface");
        RankInterfaceController controller = (RankInterfaceController) SceneManager.getInstance().getController("rankInterface");
        controller.setLabel(string);*/
    }

    /**
     *Calls method drawCards to print cards in gridPane
     * @param cardGridMatrixUpdate is the cardGrid matrix from model
     */
    public void setCardGrid(int[][] cardGridMatrixUpdate){
        cardGridController.setCardGrid(cardGridMatrixUpdate);
    }

    public void setMarbles(Marble[][] marketMatrix, Marble marbleOut){
        marketGridController.setMarbles(marketMatrix, marbleOut);
    }

    public CardGridController getCardGridController(){
        return cardGridController;
    }

    public Map<String, PersonalBoardController> getPersonalBoardControllerMap() {
        return personalBoardControllerMap;
    }

    public MarketController getMarketGridController() {
        return marketGridController;
    }

}
