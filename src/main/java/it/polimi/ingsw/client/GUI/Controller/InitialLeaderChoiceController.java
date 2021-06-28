package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.SceneManager;
import it.polimi.ingsw.common.messages.messagesToServer.DiscardInitialLeaderMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class InitialLeaderChoiceController extends AbstractController{
    private ArrayList<ImageView> cardImagesArray;
    private ArrayList<Boolean> choosenCards;
    private final String cardsPath = "cardsImage/";

    private final DropShadow highlightEffect = new DropShadow(6, Color.web("#f9dc52")); //green #65f952, red #f03030, arancione #f9dc52

    @FXML
    private GridPane cardsContainer;

    @FXML
    private Button discardButton;

    @FXML
    public void initialize(){
        cardImagesArray = new ArrayList<>();
        for (Node node:cardsContainer.getChildren()){
            this.cardImagesArray.add((ImageView) node);
        }

        choosenCards = new ArrayList<>(4);
        for(int card = 0; card < 4 ; card++)
            choosenCards.add(card,false);

        for(ImageView image : cardImagesArray){
            image.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onLeaderCardClick);
        }
        discardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onDiscardClick);

        highlightEffect.setSpread(1);

    }

    private void onLeaderCardClick(Event event){
        for(ImageView card: cardImagesArray) {
            if (event.getTarget().equals(card))
                choosenCards.set(cardImagesArray.indexOf(card), !choosenCards.get(cardImagesArray.indexOf(card)));
            if (choosenCards.get(cardImagesArray.indexOf(card)) == true) {
                card.setEffect(highlightEffect);
            }else{
                card.setEffect(null);
            }
        }
    }

    private void onDiscardClick(Event event) {
        int firstCard = 0,secondCard = 0, numOfChoosenCards = 0;
        for(int discard = 0; discard < choosenCards.size(); discard++){
            if(choosenCards.get(discard)){
                if(firstCard == 0)
                    firstCard = discard + 1;
                else if(secondCard == 0)
                    secondCard = discard + 1;
                numOfChoosenCards++;
            }
        }
        if (numOfChoosenCards == 2){
            SceneManager.getInstance().setRootFXML("loadingScreen");
            this.getGui().getMessageSender().sendMessage(new DiscardInitialLeaderMessage(getGui().getNickname(),firstCard,secondCard));
        }
    }

    public void setLeaderCards(ArrayList<Integer> initialLeaderCardsID){
        for (ImageView card: cardImagesArray){
            String thisCardPath = cardsPath + initialLeaderCardsID.get(cardImagesArray.indexOf(card)) + ".png";
            card.setImage(new Image(thisCardPath));
        }
    }

    public void resetChoices(){
        for(Boolean discard: choosenCards)
            discard = false;
    }

    public ArrayList<ImageView> getCardImagesArray() {
        return cardImagesArray;
    }

}
