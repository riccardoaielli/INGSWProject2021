package it.polimi.ingsw.client.GUI.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ChoiceBoxDevCardSpaceController extends AbstractController{

    @FXML
    private ChoiceBox<Integer> choiceBox;

    @FXML
    public void initialize() {
        choiceBox.setValue(1);
    }

    public int getValue(){
        return choiceBox.getValue();
    }

}
