package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.w3c.dom.events.Event;

public class BuyCardInterfaceController extends AbstractController {

    @FXML
    private GridPane cardGrid;
    @FXML
    private ResourceChoiceController resourceChoiceController;
    @FXML
    private Button buyCardButton;
    @FXML
    private ChoiceBoxDevCardSpaceController choiceBoxDevCardSpaceController;

    @FXML
    public void initialize() {
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        cardGrid = gameInterfaceController.getCardGridPane();

        //buyCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBuyCardButtonClick);
    }

    private void onBuyCardButtonClick(Event event){
        choiceBoxDevCardSpaceController.getValue();
        resourceChoiceController.createMaps();
        resourceChoiceController.getStrongboxMap();
        resourceChoiceController.getWarehouseDepotMap();
    }

}
