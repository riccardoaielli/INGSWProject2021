package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.PopupController;
import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.ChooseInitialResourcesMessage;
import it.polimi.ingsw.server.model.enumerations.Resource;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InitialResourceChoiceController extends PopupController {
    @FXML
    private Label resourceChoiceLabel;
    @FXML
    private GridPane initialResourceIconGridPane;
    @FXML
    private HBox initialResourceSpinnerHBox;

    @FXML
    private Button okButton;

    private Map<Resource, Integer> resourceIntegerMap = new HashMap<>();


    @FXML
    public void initialize(){
        GameInterfaceController gameInterfaceController = (GameInterfaceController) SceneManager.getInstance().getController("gameInterface");
        String nickname = SceneManager.getInstance().getGui().getNickname();
        List<Tab> personalTabs = gameInterfaceController.getPersonalTabs().getTabs();
        List<String> players = personalTabs.stream().map(Tab::getText).collect(Collectors.toList());
        int turnOfPlayer = players.indexOf(nickname) ;
        int numOfResourceToChoose = getNumOfResourceToChoose(turnOfPlayer);
        if(numOfResourceToChoose>1){
            resourceChoiceLabel.setText("Choose " + numOfResourceToChoose + " resources to add to your depot");
        }
        else{
            resourceChoiceLabel.setText("Choose " + numOfResourceToChoose + " resource to add to your depot");
        }


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

    //Method that creates maps of strongbox and warehouse depot based on the selected numbers on spinners
    private void createMaps() {
        for (Node node : initialResourceSpinnerHBox.getChildren()) {
            Spinner<Integer> spinner = (Spinner<Integer>) node;
            if(spinner.getValue() != 0){
                resourceIntegerMap.put(Resource.valueOf(spinner.getId().toUpperCase()), spinner.getValue());
            }
        }
    }

    //Method that returns how many resources a player can choose based on his order in game
    private int getNumOfResourceToChoose(int turnOfPlayer){
        switch(turnOfPlayer){
            case 0:
                return 0;
            case 1:
            case 2:
                return 1;
            //For the 4th player
            default:
                return 2;
        }
    }
}
