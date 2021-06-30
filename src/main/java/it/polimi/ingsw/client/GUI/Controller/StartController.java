package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.SceneManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class StartController extends AbstractController{

    @FXML
    private Button onlineBtn;
    @FXML
    private Button localBtn;

    @FXML
    public void initialize(){
        onlineBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::setOnlineMatch);
        localBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::setLocalMatch);

    }

    private void setOnlineMatch(Event event){
        this.getGui().setOnline(true);
        SceneManager.getInstance().setRootFXML("loadingScreen");
    }

    private void setLocalMatch(Event event){
        this.getGui().setOnline(false);
    }
}
