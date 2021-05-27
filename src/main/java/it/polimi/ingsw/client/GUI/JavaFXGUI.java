package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientMain;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXGUI extends Application {

    private static Scene scene;


    @Override
    public void start(Stage stage) throws IOException {
        GUI gui = new GUI(ClientMain.getHostAddress(), ClientMain.getPortNumber());
        SceneManager.getInstance().setGui(gui);
        SceneManager.getInstance().setStage(stage);
        SceneManager.getInstance().setRootFXML("start");
        //stage.setFullScreen(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getResource("/fxmls/"+ fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

   @Override
    public void stop(){
        Platform.exit();
        System.exit(0);
    }

}


