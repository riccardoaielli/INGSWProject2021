package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.ActivateCardProductionMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CardProductionController extends AbstractController {

    @FXML
    private ResourceChoiceController resourceChoiceController;

    private final DropShadow highlightEffect = new DropShadow(4, Color.web("#f9dc52"));
    @FXML
    private ImageView devCardImgView1;
    @FXML
    private ImageView devCardImgView2;
    @FXML
    private ImageView devCardImgView3;
    @FXML
    private Button doneButton;

    private ImageView selectedCardImageView = null;
    private int indexDevelopmentCardSpace = 0;

    @FXML
    public void initialize(){
        highlightEffect.setSpread(1);
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        String nickname = SceneManager.getInstance().getGui().getNickname();
        PersonalBoardController personalBoardController = gameInterfaceController.getPersonalBoardControllerMap().get(nickname);
        List<Image> topDevCards = personalBoardController.getTopDevCards();
        List<ImageView> popupDevCards = new ArrayList<>();
        popupDevCards.add(devCardImgView1);
        popupDevCards.add(devCardImgView2);
        popupDevCards.add(devCardImgView3);
        //If there is a card in that development card space is shown, otherwise ImageView is not shown
        for(int i = 0; i<popupDevCards.size(); i++){
            if (topDevCards.get(i)!= null){
                popupDevCards.get(i).setImage(topDevCards.get(i));
                popupDevCards.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDevCardClick);
            }
            else popupDevCards.get(i).setVisible(false);
        }

        doneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDoneButtonClick);
    }

    private void onDoneButtonClick(MouseEvent e){
        if (selectedCardImageView != null){
            resourceChoiceController.createMaps();
            getGui().getMessageSender().sendMessage(new ActivateCardProductionMessage(getGui().getNickname(), resourceChoiceController.getStrongboxMap(), resourceChoiceController.getWarehouseDepotMap(), indexDevelopmentCardSpace));
            Stage stage = (Stage) doneButton.getScene().getWindow();
            stage.close();
        }
    }

    private void onDevCardClick(Event event){
        ImageView imageView = (ImageView) event.getSource();
        if (selectedCardImageView != imageView){
            if (selectedCardImageView != null){
                selectedCardImageView.setEffect(null);
            }
            selectedCardImageView = imageView;
            selectedCardImageView.setEffect(highlightEffect);
        }
        indexDevelopmentCardSpace = Integer.parseInt(imageView.getId());
    }
}
