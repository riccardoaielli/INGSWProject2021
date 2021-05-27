package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.GUI.Controller.AbstractController;
import it.polimi.ingsw.client.GUI.Controller.StartController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    private GUI gui;
    private Stage stage;
    private Scene activeScene;
    private AbstractController activeController;
    private Map<String, AbstractController> controllerMap = new HashMap<>();

    public void setRoot(Parent root){
        if (activeScene == null){
            activeScene = new Scene(root);
            stage.setScene(activeScene);
        }
        else
        activeScene.setRoot(root);
    }

    public void setRootFXML(String fxml) {
        try {
            FXMLLoader loader = loadFXML(fxml);
            Parent root = loader.load();
            getInstance().activeController = loader.getController();
            getInstance().activeController.setGui(getInstance().gui);
            controllerMap.put(fxml, activeController);
            setRoot(root);
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

    private FXMLLoader loadFXML(String fxml) throws IOException {

        return new FXMLLoader(JavaFXGUI.class.getResource("/fxmls/" + fxml + ".fxml"));
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
