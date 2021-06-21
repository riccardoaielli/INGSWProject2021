package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.EndProduction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProductionController extends AbstractController{
    @FXML
    private Button basicProductionButton;
    @FXML
    private Button leaderCardProductionButton;
    @FXML
    private Button devCardProductionButton;
    @FXML
    private Button endProductionButton;
    @FXML
    public void initialize(){
        basicProductionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBasicProductionClick);
        leaderCardProductionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onLeaderProductionClick);
        devCardProductionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDevCardProductionClick);
        endProductionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onEndProductionClick);
    }

    private void onBasicProductionClick(MouseEvent e){
        SceneManager.getInstance().showPopup("basicProduction");
        Stage stage = (Stage) basicProductionButton.getScene().getWindow();
        stage.close();
    }

    private void onLeaderProductionClick(MouseEvent e){

    }

    private void onDevCardProductionClick(MouseEvent e){

    }

    private void onEndProductionClick(MouseEvent e){
        getGui().getMessageSender().sendMessage(new EndProduction(getGui().getNickname()));
        Stage stage = (Stage) endProductionButton.getScene().getWindow();
        stage.close();
    }

}
