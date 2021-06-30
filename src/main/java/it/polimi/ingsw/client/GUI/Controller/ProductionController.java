package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.client.GUI.PopupController;
import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.EndProduction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProductionController extends PopupController {
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

    @Override
    public void setOnClose(Stage popupStage) {
        if (getGui().isFirstProductionDone()){
            //if first production done window can't be closed
            super.setOnClose(popupStage);
        }
        else{
            popupStage.setOnCloseRequest(event ->{
                event.consume();
                getGui().setPhase(LocalPhase.MENU);
                popupStage.close();
            });
        }
    }

    private void onBasicProductionClick(MouseEvent e){
        SceneManager.getInstance().showPopup("basicProduction");
        Stage stage = (Stage) basicProductionButton.getScene().getWindow();
        stage.close();
    }

    private void onLeaderProductionClick(MouseEvent e){
        SceneManager.getInstance().showPopup("leaderProduction");
        Stage stage = (Stage) basicProductionButton.getScene().getWindow();
        stage.close();
    }

    private void onDevCardProductionClick(MouseEvent e){
        SceneManager.getInstance().showPopup("cardProduction");
        Stage stage = (Stage) basicProductionButton.getScene().getWindow();
        stage.close();
    }

    private void onEndProductionClick(MouseEvent e){
        getGui().getMessageSender().sendMessage(new EndProduction(getGui().getNickname()));
        Stage stage = (Stage) endProductionButton.getScene().getWindow();
        stage.close();
    }

}
