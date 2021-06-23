package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.GUI.Controller.AbstractController;
import it.polimi.ingsw.client.GUI.Controller.ClientDisconnectedInterfaceController;
import it.polimi.ingsw.client.GUI.Controller.ErrorController;
import it.polimi.ingsw.client.GUI.Controller.StartController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class to manage scene switches
 */
public class SceneManager {
    private static SceneManager instance = null;

    public static SceneManager getInstance(){
        if (instance == null){
            instance = new SceneManager();
        }
        return instance;
    }

    private SceneManager(){

    }

    public GUI getGui() {
        return gui;
    }

    private GUI gui;
    private Stage stage;
    private Scene activeScene;
    private AbstractController activeController;
    private Map<String, AbstractController> controllerMap = new HashMap<>();
    private Map<String, Parent> rootMap = new HashMap<>();

    private void setRoot(Parent root){
        if (activeScene == null){
            activeScene = new Scene(root);
            stage.setScene(activeScene);
        }
        else
        activeScene.setRoot(root);
    }

    public void setRootFXML(String fxml) {
        if(!controllerMap.containsKey(fxml)){
            loadSceneAndController(fxml);
        }
        activeController = this.getController(fxml);
        Platform.runLater(()-> setRoot(rootMap.get(fxml)));
    }

    public void loadSceneAndController(String fxml){
        FXMLLoader loader = loadFXML(fxml);
        try {
            Parent root = loader.load();
            rootMap.put(fxml, root);
            AbstractController controller = loader.getController();
            controller.setGui(gui);
            controllerMap.put(fxml, controller);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public AbstractController getController(String fxml){
        return controllerMap.get(fxml);
    }

    public void setStage(Stage stage) {
        SceneManager.getInstance().stage = stage;
    }

    public FXMLLoader loadFXML(String fxml){
        return new FXMLLoader(JavaFXGUI.class.getResource("/fxmls/" + fxml + ".fxml"));
    }

    public void setGui(GUI gui) {
        this.gui = gui;
        //Game Interface is loaded and can be updated before being shown
        loadSceneAndController("gameInterface");
    }

    public AbstractController getActiveController() {
        return activeController;
    }


    /**
     * Method used to display an alert window with an error message
     * @param errorMessage The error message that will be displayed
     */
    public void showError(String errorMessage){
        Platform.runLater(() -> {
            Stage errorStage = new Stage();
            FXMLLoader errorPopupLoader = loadFXML("error");
            try {
                Parent root = errorPopupLoader.load();
                ErrorController errorController = errorPopupLoader.getController();
                errorController.setGui(gui);

                errorStage.setScene(new Scene(root));
                errorStage.setAlwaysOnTop(true);
                errorStage.initStyle(StageStyle.UNDECORATED);
                errorController.setStringError(errorMessage);
                errorStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method used to display an alert window witt the client disconnected
     * @param stringMessage The error message that will be displayed
     */
    public void showClientDisconnectedInterface(String stringMessage){
        Platform.runLater(() -> {
            Stage showClientDisconnectedInterfaceStage = new Stage();
            FXMLLoader showClientDisconnectedInterfacePopupLoader = loadFXML("clientDisconnectedInterface");
            try {
                Parent root = showClientDisconnectedInterfacePopupLoader.load();
                ClientDisconnectedInterfaceController showClientDisconnectedInterfaceController = showClientDisconnectedInterfacePopupLoader.getController();
                showClientDisconnectedInterfaceController.setGui(gui);

                showClientDisconnectedInterfaceStage.setScene(new Scene(root));
                showClientDisconnectedInterfaceStage.setAlwaysOnTop(true);
                showClientDisconnectedInterfaceStage.initStyle(StageStyle.UNDECORATED);
                showClientDisconnectedInterfaceController.setStringMessage(stringMessage);
                showClientDisconnectedInterfaceStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method used to show a popup over the current scene
     * @param fxml Name of the .fxml file from which the scene in the popup stage will be loaded
     */
    public void showPopup(String fxml){
        Platform.runLater(() -> {
            Stage popupStage = new Stage();
            FXMLLoader popupLoader = loadFXML(fxml);
            try {
                Parent root = popupLoader.load();
                AbstractController abstractController = popupLoader.getController();
                abstractController.setGui(gui);
                popupStage.setScene(new Scene(root));
                //popupStage.setAlwaysOnTop(true);
                popupStage.initStyle(StageStyle.UNDECORATED);
                popupStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Scene getActiveScene() {
        return activeScene;
    }
}
