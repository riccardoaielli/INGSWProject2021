package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.DevelopmentCardColor;

import java.util.*;
import java.io.*;

/**
 * This class represents the development card grid
 */
public class CardGrid {

    private int i, j;
    private final int maxRow = 3, maxColumn = 4;

    private Stack<DevelopmentCard>[][] cardGridMatrix;
    private DevelopmentCard cardToBeReturned;

    public CardGrid(){


    }

    /**
     * Returns the top card of the specified stack
     */
    public DevelopmentCard getCard(int x, int y){

        //cardToBeReturned = cardGridMatrix[x][y].peek();

        return cardToBeReturned;

    }

    /**
     * Implementation of buyCard returns the top card of the specified stack and deletes it from the stack
     */
    public DevelopmentCard buyCard(int x, int y){

        //cardToBeReturned = cardGridMatrix[x][y].pop();

        return cardToBeReturned;

    }

    /**
     * Implementation of remove, it removes one developmentCard whit the specified color from the card grid starting from level one to level three
     * */
    public void remove(DevelopmentCardColor color){

        /*for(i=maxRow; i>0; i--)
            for(j=0; j<maxColumn; j++){
                if(getCard(i,j).getColor().equals(color)){
                    cardGridMatrix[i][j].pop();
                    return;
                }
            }*/

    }

}
