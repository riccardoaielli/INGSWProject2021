package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.common.messages.messagesToServer.DiscardLeaderMessage;
import it.polimi.ingsw.common.messages.messagesToServer.SwapMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SwapInterfaceController extends AbstractController{

    @FXML
    private ChoiceBox<Integer> choiceBox1;

    @FXML
    private ChoiceBox<Integer> choiceBox2;

    @FXML
    private Button doneButton;

    @FXML
    public void initialize() {
        choiceBox1.setValue(1);
        choiceBox2.setValue(1);
        doneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDoneButtonClick);
    }

    private void onDoneButtonClick(Event event){
        getGui().getMessageSender().sendMessage(new SwapMessage(getGui().getNickname(), choiceBox1.getValue(), choiceBox2.getValue()));
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }

}
