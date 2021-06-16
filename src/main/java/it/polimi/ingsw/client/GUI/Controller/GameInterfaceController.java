package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.server.model.enumerations.Marble;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInterfaceController extends AbstractController {
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
