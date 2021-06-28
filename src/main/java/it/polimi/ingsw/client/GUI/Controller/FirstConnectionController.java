package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.JavaFXGUI;
import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.CreateMatchReplyMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class FirstConnectionController extends AbstractController {
    @FXML
    private TextField nickname;
    @FXML
    private ChoiceBox<Integer> choiceBox;
    @FXML
    private Button joinButton;

    @FXML
    public void initialize(){
        joinButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinClick);
        choiceBox.setValue(1);
    }

    private void onJoinClick(Event event){
        String nickname = this.nickname.getText();
        int numOfPlayers = choiceBox.getValue();
        System.out.println(nickname + numOfPlayers);
        this.getGui().setMyNickname(nickname);
        SceneManager.getInstance().setRootFXML("loadingScreen");
        this.getGui().getMessageSender().sendMessage(new CreateMatchReplyMessage(nickname, numOfPlayers));
    }





}
