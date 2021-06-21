package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ErrorController extends AbstractController {
    @FXML
    private Label stringError;
    @FXML
    private Button okButton;

    @FXML
    public void initialize() {
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onOkClick);
    }

    private void onOkClick(MouseEvent event){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void setStringError(String stringError){
        this.stringError.setText(stringError);
    }
}
