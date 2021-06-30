package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.client.GUI.PopupController;
import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.TakeFromMarketMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TakeFromMarketController extends PopupController {
    //MARKET
    @FXML
    private MarketController marketGridController;

    @FXML
    private Button doneButton;

    @FXML
    public void initialize(){
        GameInterfaceController gameInterfaceController = (GameInterfaceController)SceneManager.getInstance().getController("gameInterface");
        MarketController marketController = gameInterfaceController.getMarketGridController();
        marketGridController.setMarbles(marketController.getMarketMatrix(), marketController.getMarbleOut());
        doneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDoneClick);
    }

    public void onDoneClick(MouseEvent e){
        if( marketGridController.getRowOrColumn() != -1 && marketGridController.getSelectedValue() != -1){
            getGui().getMessageSender().sendMessage(new TakeFromMarketMessage(getGui().getNickname(), marketGridController.getRowOrColumn(), marketGridController.getSelectedValue()));
            Stage stage = (Stage) doneButton.getScene().getWindow();
            stage.close();
        }
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
