package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.TakeFromMarketMessage;
import it.polimi.ingsw.server.model.enumerations.Marble;
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
    private ToolBar buttonToolBar;

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
        //ObservableList<Node> toolbarElement = buttonToolBar.getItems();
        marketButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMarketButtonClick);
        buyCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBuyCardButtonClick);
        productionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onProductionButtonClick);
        activateLeaderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onActivateLeaderButtonClick);
        discardLeaderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDiscardLeaderButtonClick);
        rearrangeWHouseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onRearrangeWHouseButtonClick);
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onQuitButtonClick);
    }

    private void onMarketButtonClick(Event event){
        SceneManager.getInstance().showPopup("");
    }

    private void onBuyCardButtonClick(Event event){
        SceneManager.getInstance().showPopup("buyCardInterface");
    }

    private void onProductionButtonClick(Event event){
        SceneManager.getInstance().showPopup("");
    }

    private void onActivateLeaderButtonClick(Event event){
        SceneManager.getInstance().showPopup("");
    }

    private void onDiscardLeaderButtonClick(Event event){
        SceneManager.getInstance().showPopup("");
    }

    private void onRearrangeWHouseButtonClick(Event event){
        SceneManager.getInstance().showPopup("");
    }

    private void onQuitButtonClick(Event event){
        SceneManager.getInstance().showPopup("");
    }


    /**
     * Method to set the tabs of all the players in the GUI
     * @param playersOrder
     */
    public void setPlayers(List<String> playersOrder){
        for (int order = 0; order < playersOrder.size(); order++){
            FXMLLoader tabContent = SceneManager.getInstance().loadFXML("personalBoard");
            try {
                Tab playerTab = new Tab(playersOrder.get(order), tabContent.load());
                personalBoardControllerMap.put(playersOrder.get(order), tabContent.getController());
                personalTab.getTabs().add(playerTab);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public GridPane getCardGridPane(){
        return cardGridController.getCardGridPane();
    }

    public Map<String, PersonalBoardController> getPersonalBoardControllerMap() {
        return personalBoardControllerMap;
    }

}
