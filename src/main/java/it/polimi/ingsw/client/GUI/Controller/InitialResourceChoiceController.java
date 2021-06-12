package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.common.messages.messagesToServer.ChooseInitialResourcesMessage;
import it.polimi.ingsw.server.model.enumerations.Resource;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class InitialResourceChoiceController extends AbstractController {
    @FXML
    private GridPane initialResourceIconGridPane;
    @FXML
    private HBox initialResourceSpinnerHBox;

    @FXML
    private Button okButton;

    private Map<Resource, Integer> resourceIntegerMap = new HashMap<>();


    @FXML
    public void initialize(){
        for (Node node : initialResourceIconGridPane.getChildren()) {
            ImageView imgView = (ImageView) node;
            imgView.setImage(new Image("resourcesImage/" + imgView.getId() + ".png"));
        }

        for (Node node : initialResourceSpinnerHBox.getChildren()) {
            Spinner<Integer> spinner = (Spinner<Integer>) node;
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50,0);
            spinner.setValueFactory(spinnerValueFactory);
        }
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onOkClick);
    }

    private void onOkClick(MouseEvent event){
        createMaps();
        getGui().getMessageSender().sendMessage(new ChooseInitialResourcesMessage(getGui().getNickname(), resourceIntegerMap));
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    private void createMaps() {
        for (Node node : initialResourceSpinnerHBox.getChildren()) {
            Spinner<Integer> spinner = (Spinner<Integer>) node;
            if(spinner.getValue() != 0){
                resourceIntegerMap.put(Resource.valueOf(spinner.getId().toUpperCase()), spinner.getValue());
            }
        }
    }

}
