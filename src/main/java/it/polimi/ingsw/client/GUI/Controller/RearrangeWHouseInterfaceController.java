package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.client.GUI.PopupController;
import it.polimi.ingsw.client.GUI.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RearrangeWHouseInterfaceController extends PopupController {
    @FXML
    private Button swapButton;
    @FXML
    private Button moveButton;

    public void initialize(){
        swapButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::swapButtonClick);
        moveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::moveButtonClick);
    }

    private void swapButtonClick(MouseEvent e){
        SceneManager.getInstance().showPopup("swapInterface");
        Stage stage = (Stage) swapButton.getScene().getWindow();
        stage.close();
    }

    private void moveButtonClick(MouseEvent e){
        SceneManager.getInstance().showPopup("moveInterface");
        Stage stage = (Stage) moveButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setOnClose(Stage popupStage) {
        popupStage.setOnCloseRequest(event ->{
            event.consume();
            getGui().setPhase(LocalPhase.MENU);
            popupStage.close();
        });
    }
}
