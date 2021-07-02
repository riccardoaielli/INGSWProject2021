package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.PopupController;
import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.client.LocalPhase;
import it.polimi.ingsw.common.messages.messagesToServer.ActivateBasicProductionMessage;
import it.polimi.ingsw.common.messages.messagesToServer.ActivateLeaderProductionMessage;
import it.polimi.ingsw.server.model.enumerations.Resource;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LeaderProductionController extends PopupController{
    @FXML
    private ResourceChoiceController resourceChoiceController;

    private final DropShadow highlightEffect = new DropShadow(4, Color.web("#f9dc52"));
    private ImageView selectedResourceImageView = null;
    private Resource selectedResource = null;
    private ImageView selectedCardImageView = null;
    private Integer numLeaderCard = 0;

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
    private ImageView leaderOneImgView;
    @FXML
    private ImageView leaderTwoImgView;

    @FXML
    public void initialize(){
        highlightEffect.setSpread(1);
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        String nickname = SceneManager.getInstance().getGui().getNickname();
        PersonalBoardController personalBoardController = gameInterfaceController.getPersonalBoardControllerMap().get(nickname);

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
        if (selectedResource == null || selectedCardImageView == null){
            //show warning
        }
        else{
            getGui().getMessageSender().sendMessage(new ActivateLeaderProductionMessage(getGui().getNickname(), resourceChoiceController.getStrongboxMap(), resourceChoiceController.getWarehouseDepotMap(), numLeaderCard , selectedResource));
            Stage stage = (Stage) doneButton.getScene().getWindow();
            stage.close();
        }
    }

    private void onImgLeaderCardClick(Event event){
        ImageView imageView = (ImageView) event.getTarget();
        if (selectedCardImageView != imageView){
            if (selectedCardImageView != null){
                selectedCardImageView.setEffect(null);
            }
            selectedCardImageView = imageView;
            selectedCardImageView.setEffect(highlightEffect);
        }
        numLeaderCard = Integer.parseInt(imageView.getId());
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
