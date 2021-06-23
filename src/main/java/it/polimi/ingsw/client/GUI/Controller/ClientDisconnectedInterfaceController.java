package it.polimi.ingsw.client.GUI.Controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClientDisconnectedInterfaceController extends AbstractController{

    @FXML
    private Button okButton;

    @FXML
    private Label label;

    @FXML
    public void initialize() {
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::okButtonClick);
    }

    public void setStringMessage(String string){
        label.setText(string);
    }

    private void okButtonClick(Event event){
        getGui().exitGame();
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
