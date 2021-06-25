package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.AddToWarehouseMessage;
import it.polimi.ingsw.common.messages.messagesToServer.DiscardResourcesFromMarketMessage;
import it.polimi.ingsw.server.model.enumerations.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class AddToWarehouseController extends AbstractController{
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
    private Button discardButton;
    @FXML
    private ChoiceBox<Integer> depotShelf;
    @FXML
    public void initialize(){
        depotShelf.setValue(1);
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

        //If first turn, resources cannot be discarded
        if(SceneManager.getInstance().getGui().isFirstTurn()){
            discardButton.setVisible(false);
        }
        else{
            discardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDiscardButtonClick);
        }
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
        if (selectedResource == null){
            //show warning
        }
        else{
            Map<Resource, Integer> singleResourceMap = new HashMap<>();
            singleResourceMap.put(selectedResource, 1);
            int depotShelfInt = depotShelf.getValue();
            getGui().getMessageSender().sendMessage(new AddToWarehouseMessage(getGui().getNickname(), depotShelfInt, singleResourceMap));
            Stage stage = (Stage) doneButton.getScene().getWindow();
            stage.close();
        }
    }

    private void onDiscardButtonClick(MouseEvent e){
        getGui().getMessageSender().sendMessage(new DiscardResourcesFromMarketMessage(getGui().getNickname()));
        Stage stage = (Stage) discardButton.getScene().getWindow();
        stage.close();
    }
}
