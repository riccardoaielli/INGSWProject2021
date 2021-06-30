package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.GUI.Controller.AbstractController;
import javafx.event.Event;
import javafx.stage.Stage;

public abstract class PopupController extends AbstractController {
    public void setOnClose(Stage popupStage) {
        popupStage.setOnCloseRequest(Event::consume);
    }
}
