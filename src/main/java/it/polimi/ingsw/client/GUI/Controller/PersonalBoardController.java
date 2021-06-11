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
        //TODO also possible to set myNickname in personal board and put chosen leaderCards if personalBoard.getMyNickname.equals(gui.getmyNickname) else set bakc cover of leader cards
        //Setting the back of leader cards
        String backLeaderCardPath = "cardsImage/65.png";
        setLeaderCard1(new Image(backLeaderCardPath));
        setLeaderCard2(new Image(backLeaderCardPath));
    }


}
