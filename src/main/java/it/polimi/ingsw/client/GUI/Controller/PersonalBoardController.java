package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PersonalBoardController extends  AbstractController{
    public void setLeaderCard1(Image leaderCard1) {
        this.leaderCard1.setImage(leaderCard1);
    }

    public void setLeaderCard2(Image leaderCard2) {
        this.leaderCard2.setImage(leaderCard2);
    }

    @FXML
    private ImageView leaderCard1;

    @FXML
    private ImageView leaderCard2;

    @FXML
    public void initialize(){
        //TODO modificarlo in un metodo che viene eseguito solo per la personal board di myNickname, eventualmente anche un metodo pubblico chiamato da fuori.
        // Serve avere accesso al nickname della personal board, devo settarlo su personal board controller quando creo le tab
        //Setting the not discarded leader cards
        InitialLeaderChoiceController initialLeaderChoiceController = (InitialLeaderChoiceController) SceneManager.getInstance().getController("InitialLeaderChoice");
        setLeaderCard1(initialLeaderChoiceController.getCardImagesArray().get(0).getImage());
        setLeaderCard2(initialLeaderChoiceController.getCardImagesArray().get(1).getImage());
    }


}
