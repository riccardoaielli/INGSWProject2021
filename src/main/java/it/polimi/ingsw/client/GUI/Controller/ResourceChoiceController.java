package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.common.messages.messagesToServer.DiscardInitialLeaderMessage;
import it.polimi.ingsw.server.model.enumerations.Resource;
import javafx.event.Event;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResourceChoiceController extends AbstractController{

    @FXML
    private GridPane depotResourceGridPane;
    @FXML
    private HBox depotSpinnerHBox;
    @FXML
    private GridPane strongboxResourceGridPane;
    @FXML
    private HBox strongboxSpinnerHBox;

    public Map getWarehouseDepotMap() {
        return warehouseDepotMap;
    }

    public Map getStrongboxMap() {
        return strongboxMap;
    }

    private Map warehouseDepotMap = new HashMap<Resource, Integer>();
    private Map strongboxMap = new HashMap<Resource, Integer>();

    @FXML
    public void initialize() {

        for (Node node : depotResourceGridPane.getChildren()) {
            ImageView imgView = (ImageView) node;
            imgView.setImage(new Image("resourcesImage/" + imgView.getId() + ".png"));
        }

        for (Node node : strongboxResourceGridPane.getChildren()) {
            ImageView imgView = (ImageView) node;
            imgView.setImage(new Image("resourcesImage/" + imgView.getId() + ".png"));
        }

        for (Node node : depotSpinnerHBox.getChildren()) {
            Spinner<Integer> spinner = (Spinner<Integer>) node;
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50,0);
            spinner.setValueFactory(spinnerValueFactory);
        }

        for (Node node : strongboxSpinnerHBox.getChildren()) {
            Spinner<Integer> spinner = (Spinner<Integer>) node;
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50,0);
            spinner.setValueFactory(spinnerValueFactory);
        }

    }

    public void createMaps() {

        for (Node node : depotSpinnerHBox.getChildren()) {
            Spinner<Integer> spinner = (Spinner<Integer>) node;
            if(spinner.getValue() != 0){
                warehouseDepotMap.put(Resource.valueOf(spinner.getId().toUpperCase()), spinner.getValue());
                //System.out.println(spinner.getId() + spinner.getValue());
            }
        }

        for (Node node : strongboxSpinnerHBox.getChildren()) {
            Spinner<Integer> spinner = (Spinner<Integer>) node;
            if(spinner.getValue() != 0){
                strongboxMap.put(Resource.valueOf(spinner.getId().toUpperCase()), spinner.getValue());
                //System.out.println(spinner.getId() + spinner.getValue());
            }
        }

    }


}
