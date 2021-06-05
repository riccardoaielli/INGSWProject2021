package it.polimi.ingsw.server.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.common.messages.messagesToClient.CardGridUpdate;
import it.polimi.ingsw.common.messages.messagesToClient.LorenzoDrawUpdate;
import it.polimi.ingsw.common.utils.CardGridParser;
import it.polimi.ingsw.common.utils.observe.MessageObservable;
import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.server.model.exceptions.NoCardException;

import java.lang.reflect.Type;
import java.util.*;
import java.io.*;

/**
 * This class represents the development card grid
 */
public class CardGrid extends MessageObservable {

    private int i, j;
    private final int maxRow = 3, maxColumn = 4;

    boolean alreadyLost = false;

    private Match matchToNotify;

    private Stack<DevelopmentCard>[][] cardGridMatrix;
    private DevelopmentCard cardToBeReturned;


    /**
     * This constructor creates the cardGrid structure, reading the development cards form the json file developmentCards.json and initializes each cards stack randomly
     */
    public CardGrid(){

        CardGridParser cardGridParser = new CardGridParser();
        cardGridMatrix = cardGridParser.parse();

        for(i=0; i<maxRow; i++)
            for(j=0; j<maxColumn; j++){
                Collections.shuffle(cardGridMatrix[i][j]);
            }

    }

    /**
     * This method sets the match to notify and is called after the constructor of the class
     * */
    public void AddMatchToNotify(Match matchToNotify){
        this.matchToNotify = matchToNotify;
    }


    /**
     * Returns the top card of the specified stack
     */
    public DevelopmentCard getCard(int x, int y) throws NoCardException {

        try{
            cardToBeReturned = cardGridMatrix[x][y].peek();
        }catch(EmptyStackException e){
            throw new NoCardException();
        }

        return cardToBeReturned;

    }

    /**
     * Implementation of buyCard returns the top card of the specified stack and deletes it from the stack
     */
    public DevelopmentCard buyCard(int x, int y)  throws NoCardException{

        try{
            cardToBeReturned = cardGridMatrix[x][y].pop();
        }catch(EmptyStackException e){
            throw new NoCardException();
        }
        doNotify();
        return cardToBeReturned;

    }

    /**
     * Implementation of remove, it removes one developmentCard whit the specified color from the card grid starting from level one to level three
     * */
    public String remove(DevelopmentCardColor color) {

        int column = -1;

        if (!alreadyLost) {
            //looking for the correct column according to the color parameter
            for (i = 0; i < maxRow; i++)
                for (j = 0; j < maxColumn; j++) {
                    try {
                        if ((!cardGridMatrix[i][j].empty()) && getCard(i, j).getColor().equals(color)) {
                            column = j;
                        }
                    } catch (NoCardException e) {
                        e.printStackTrace();
                    }
                }


            for (i = maxRow - 1; i >= 0; i--) {
                if (!cardGridMatrix[i][column].empty()) {//if the bottom stack is empty, don't do anything and go to the upper stack
                    cardGridMatrix[i][column].pop(); //removing only one card of the lowest level available of the color passed by parameter
                    notifyObservers(new LorenzoDrawUpdate("Lorenzo",i,column));
                    if (cardGridMatrix[0][column].empty() && cardGridMatrix[1][column].empty() && cardGridMatrix[2][column].empty()) {
                        alreadyLost = true;//one column is completely empty (column are of the same color)
                        //TODO avviso match fine partita, hai perso. Metti void e rimuovi stringhe
                        //matchToNotify.endGame("Hai Perso");
                        return "Hai Perso";
                    }
                    return "Non hai ancora perso"; //fondamentale per non creare errori
                }
            }
        }
        return "Hai gia perso"; //caso che non dovrebbe mai verificarsi
    }

    public void doNotify(){
        int[][] cardGridMatrixUpdate = new int[maxRow][maxColumn];

        for (i = 0; i < maxRow; i++)
            for (j = 0; j < maxColumn; j++) {
                cardGridMatrixUpdate[i][j] = cardGridMatrix[i][j].peek().getId();
            }

        notifyObservers(new CardGridUpdate(cardGridMatrixUpdate));
    }

}
