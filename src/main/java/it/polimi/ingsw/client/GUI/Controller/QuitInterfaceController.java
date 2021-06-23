package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.common.messages.messagesToServer.ActivateLeaderMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class QuitInterfaceController extends  AbstractController{

    @FXML
    private Button quitButton;
    @FXML
    private Button noButton;

    @FXML
    public void initialize() {
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::quitButtonClick);
        noButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::noButtonClick);
    }

    private void quitButtonClick(Event event){
        getGui().exitGame();
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    private void noButtonClick(Event event){
        Stage stage = (Stage) noButton.getScene().getWindow();
        stage.close();
    }
}
