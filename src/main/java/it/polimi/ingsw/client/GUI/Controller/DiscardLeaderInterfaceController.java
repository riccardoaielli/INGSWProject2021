package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.LocalPhase;
import it.polimi.ingsw.client.GUI.PopupController;
import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.DiscardLeaderMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DiscardLeaderInterfaceController extends PopupController {

    @FXML
    private Button discardLeaderButton;
    @FXML
    private ImageView leaderOneImgView;
    @FXML
    private ImageView leaderTwoImgView;

    private ImageView selectedImageView;

    private Integer numLeaderCard = 0; //inizializzo a zero ossia non ho selezionato la carta

    private final DropShadow highlightEffect = new DropShadow(6, Color.web("#f03030")); //green #65f952, red #f03030, arancione #f9dc52

    @FXML
    public void initialize() {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");

        String nickname = SceneManager.getInstance().getGui().getNickname();
        PersonalBoardController personalBoardController = gameInterfaceController.getPersonalBoardControllerMap().get(nickname);

        highlightEffect.setSpread(1);

        Image leaderOneImg = personalBoardController.getLeaderCard1Image();
        Image leaderTwoImg = personalBoardController.getLeaderCard2Image();

        if(leaderOneImg==null){
            leaderOneImgView.setVisible(false);
        }else{
            leaderOneImgView.setImage(leaderOneImg);
        }

        if(leaderTwoImg==null){
            leaderTwoImgView.setVisible(false);
        }else{
            leaderTwoImgView.setImage(leaderTwoImg);
        }

        leaderOneImgView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onImgLeaderCardClick);
        leaderTwoImgView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onImgLeaderCardClick);

        discardLeaderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBuyCardButtonClick);

        discardLeaderButton.setDisable(true);
    }

    private void onBuyCardButtonClick(Event event){
        getGui().getMessageSender().sendMessage(new DiscardLeaderMessage(getGui().getNickname(), numLeaderCard));
        Stage stage = (Stage) discardLeaderButton.getScene().getWindow();
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
        discardLeaderButton.setDisable(false);
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
