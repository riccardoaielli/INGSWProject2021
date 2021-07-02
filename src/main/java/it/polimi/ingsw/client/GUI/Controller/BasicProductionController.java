package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.PopupController;
import it.polimi.ingsw.common.messages.messagesToServer.ActivateBasicProductionMessage;
import it.polimi.ingsw.server.model.enumerations.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class BasicProductionController extends PopupController {
    @FXML
    private ResourceChoiceController resourceChoiceController;
    private final DropShadow highlightEffect = new DropShadow(4, Color.web("#f9dc52"));
    private ImageView selectedResourceImageView = null;
    private Resource selectedResource = null;

    @FXML
    private ImageView shieldImgV;
    @FXML
    private ImageView coinImgV;
    @FXML
    private ImageView stoneImgV;
    @FXML
    private ImageView servantImgV;
    @FXML
    private Button doneButton;

    @FXML
    public void initialize(){
        //TODO extract resource choice in a separate fxml
        highlightEffect.setSpread(1);
        //shieldImgV.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onResourceClick);
        shieldImgV.setOnMouseClicked(e ->{
            highlightResource(shieldImgV);
            selectedResource = Resource.SHIELD;
        } );
        coinImgV.setOnMouseClicked(e ->{
            highlightResource(coinImgV);
            selectedResource = Resource.COIN;
        } );
        stoneImgV.setOnMouseClicked(e ->{
            highlightResource(stoneImgV);
            selectedResource = Resource.STONE;
        } );
        servantImgV.setOnMouseClicked(e ->{
            highlightResource(servantImgV);
            selectedResource = Resource.SERVANT;
        } );
        doneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDoneButtonClick);
    }

    private void highlightResource(ImageView resource){
        if (selectedResourceImageView != resource){
            if (selectedResourceImageView != null){
                selectedResourceImageView.setEffect(null);
            }
            selectedResourceImageView = resource;
            selectedResourceImageView.setEffect(highlightEffect);
        }
    }

    private void onDoneButtonClick(MouseEvent e){
        resourceChoiceController.createMaps();
        if (selectedResource == null){
            //show warning
        }
        else{
            getGui().getMessageSender().sendMessage(new ActivateBasicProductionMessage(getGui().getNickname(), resourceChoiceController.getStrongboxMap(), resourceChoiceController.getWarehouseDepotMap(), selectedResource));
            Stage stage = (Stage) doneButton.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void setOnClose(Stage popupStage) {
        popupStage.setOnCloseRequest(event ->{
            event.consume();
            popupStage.close();
            getGui().askProduction();
        });
    }
}
