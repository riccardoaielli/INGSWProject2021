package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.common.messages.messagesToServer.ActivateLeaderMessage;
import it.polimi.ingsw.common.messages.messagesToServer.BuyDevelopmentCardMessage;
import javafx.event.Event;
import it.polimi.ingsw.client.GUI.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BuyCardInterfaceController extends AbstractController {

    @FXML
    private GridPane cardGrid;
    @FXML
    private CardGridController cardGridController;
    @FXML
    private ResourceChoiceController resourceChoiceController;
    @FXML
    private Button buyCardButton;
    @FXML
    private ChoiceBoxDevCardSpaceController choiceBoxDevCardSpaceController;
    @FXML
    private ImageView leaderOneImgView;
    @FXML
    private ImageView leaderTwoImgView;

    private ImageView selectedImageView;

    private Integer numLeaderCard = 0; //inizializzo a zero ossia non ho selezionato la carta

    private final DropShadow highlightEffect = new DropShadow(6, Color.web("#f9dc52"));

    @FXML
    public void initialize() {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        cardGridController.setCardGrid(gameInterfaceController.getCardGridController().getCardGridMatrixCurrent());

        String nickname = SceneManager.getInstance().getGui().getNickname();
        PersonalBoardController personalBoardController = gameInterfaceController.getPersonalBoardControllerMap().get(nickname);

        highlightEffect.setSpread(1);

        leaderOneImgView.setImage(personalBoardController.getLeaderCard1Image());
        leaderTwoImgView.setImage(personalBoardController.getLeaderCard2Image());

        leaderOneImgView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onImgLeaderCardClick);
        leaderTwoImgView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onImgLeaderCardClick);

        buyCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBuyCardButtonClick);
    }

    private void onBuyCardButtonClick(Event event){

        resourceChoiceController.createMaps();
        Integer selx = cardGridController.getSelx();
        Integer sely = cardGridController.getSely();

        //da spostare
        getGui().getMessageSender().sendMessage(new BuyDevelopmentCardMessage(getGui().getNickname(), selx, sely, resourceChoiceController.getStrongboxMap(), resourceChoiceController.getWarehouseDepotMap(), numLeaderCard, choiceBoxDevCardSpaceController.getValue()));

        Stage stage = (Stage) buyCardButton.getScene().getWindow();
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

}
