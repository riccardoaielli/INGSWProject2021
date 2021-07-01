package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.LocalPhase;
import it.polimi.ingsw.client.GUI.PopupController;
import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.ActivateLeaderMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ActivateLeaderInterfaceController extends PopupController {

    @FXML
    private Button activateLeaderButton;
    @FXML
    private ImageView leaderOneImgView;
    @FXML
    private ImageView leaderTwoImgView;

    private ImageView selectedImageView;

    private Integer numLeaderCard = 0; //inizializzo a zero ossia non ho selezionato la carta

    private final DropShadow highlightEffect = new DropShadow(6, Color.web("#65f952")); //green #65f952, red #f03030, arancione #f9dc52

    @FXML
    public void initialize() {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");

        String nickname = SceneManager.getInstance().getGui().getNickname();
        PersonalBoardController personalBoardController = gameInterfaceController.getPersonalBoardControllerMap().get(nickname);

        highlightEffect.setSpread(1);

        leaderOneImgView.setImage(personalBoardController.getLeaderCard1Image());
        leaderTwoImgView.setImage(personalBoardController.getLeaderCard2Image());

        leaderOneImgView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onImgLeaderCardClick);
        leaderTwoImgView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onImgLeaderCardClick);

        activateLeaderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBuyCardButtonClick);
    }

    private void onBuyCardButtonClick(Event event){
        getGui().getMessageSender().sendMessage(new ActivateLeaderMessage(getGui().getNickname(), numLeaderCard));
        Stage stage = (Stage) activateLeaderButton.getScene().getWindow();
        stage.close();
    }

    private void onImgLeaderCardClick(Event event){
        ImageView imageView = (ImageView) event.getTarget();
        if (selectedImageView != imageView){
            if (selectedImageView != null){
                selectedImageView.setEffect(null);
            }
            selectedImageView = imageView;
            selectedImageView.setEffect(highlightEffect);
        }
        numLeaderCard = Integer.parseInt(imageView.getId());
    }

    @Override
    public void setOnClose(Stage popupStage) {
        popupStage.setOnCloseRequest(event ->{
            event.consume();
            getGui().setPhase(LocalPhase.MENU);
            popupStage.close();
        });
    }

}
