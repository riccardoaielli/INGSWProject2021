package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.CreateMatchReplyMessage;
import it.polimi.ingsw.common.messages.messagesToServer.NicknameReplyMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class NicknameController extends AbstractController {
    @FXML
    private TextField nickname;

    @FXML
    private Button joinButton;

    @FXML
    public void initialize(){
        joinButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinClick);
    }

    private void onJoinClick(Event event){
        String nickname = this.nickname.getText();
        this.getGui().setMyNickname(nickname);
        SceneManager.getInstance().setRootFXML("loadingScreen");
        this.getGui().getMessageSender().sendMessage(new NicknameReplyMessage(nickname));
    }

}
