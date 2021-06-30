package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.PopupController;
import it.polimi.ingsw.common.messages.messagesToServer.MoveMessage;
import it.polimi.ingsw.common.messages.messagesToServer.SwapMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MoveInterfaceController extends PopupController {

    @FXML
    private ChoiceBox<Integer> choiceBox1;

    @FXML
    private ChoiceBox<Integer> choiceBox2;

    @FXML
    private ChoiceBox<Integer> choiceBox3;

    @FXML
    private Button doneButton;

    @FXML
    public void initialize() {
        choiceBox1.setValue(1);
        choiceBox2.setValue(1);
        choiceBox3.setValue(1);
        doneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDoneButtonClick);
    }

    private void onDoneButtonClick(Event event){
        getGui().getMessageSender().sendMessage(new MoveMessage(getGui().getNickname(), choiceBox1.getValue(), choiceBox2.getValue(), choiceBox3.getValue()));
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }
}
