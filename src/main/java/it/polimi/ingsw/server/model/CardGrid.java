package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.messages.messagesToClient.CardGridUpdate;
import it.polimi.ingsw.common.messages.messagesToClient.LorenzoDrawUpdate;
import it.polimi.ingsw.common.utils.CardGridParser;
import it.polimi.ingsw.common.utils.observe.MessageObservable;
import it.polimi.ingsw.server.model.comparators.DemoDevelopmentCardsComparator;
import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.server.model.exceptions.NoCardException;

import java.util.*;

/**
 * This class represents the development card grid
 */
public class CardGrid extends MessageObservable {
    private int row, column;
    private final int maxRow = 3, maxColumn = 4;
    boolean alreadyLost = false;
    private Match matchToNotify;
    private Stack<DevelopmentCard>[][] cardGridMatrix;
    private DevelopmentCard cardToBeReturned;

    /**
     * This constructor creates the cardGrid structure, reading the development cards form the json file developmentCards.json and initializes each cards stack randomly
     */
    public CardGrid(boolean demo){
        CardGridParser cardGridParser = new CardGridParser();
        cardGridMatrix = cardGridParser.parse();
        for (row = 0; row < maxRow; row++)
            for (column = 0; column < maxColumn; column++) {
                if(demo)
                   cardGridMatrix[row][column].sort(new DemoDevelopmentCardsComparator());
                else
                    Collections.shuffle(cardGridMatrix[row][column]);
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
            throw new NoCardException("\"There is no card in the specified position of card grid\"");
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
            throw new NoCardException("There is no card in the specified position of card grid");
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
            for (row = 0; row < maxRow; row++)
                for (this.column = 0; this.column < maxColumn; this.column++) {
                    try {
                        if ((!cardGridMatrix[row][this.column].empty()) && getCard(row, this.column).getColor().equals(color)) {
                            column = this.column;
                        }
                    } catch (NoCardException ignored) { }
                }
            for (row = maxRow - 1; row >= 0; row--) {
                if (!cardGridMatrix[row][column].empty()) {//if the bottom stack is empty, don't do anything and go to the upper stack
                    cardGridMatrix[row][column].pop(); //removing only one card of the lowest level available of the color passed by parameter
                    notifyObservers(new LorenzoDrawUpdate("Lorenzo", row,column));
                    if (cardGridMatrix[0][column].empty() && cardGridMatrix[1][column].empty() && cardGridMatrix[2][column].empty()) {
                        alreadyLost = true;//one column is completely empty (column are of the same color)
                        if(matchToNotify != null) matchToNotify.update(true);
                        return "Hai Perso";
                    }
                    return "Non hai ancora perso"; //fondamentale per non creare errori
                }
            }
        }
        return "Hai gia perso"; //caso che non dovrebbe mai verificarsi
    }

    /**
     * this method sends a massage with an update of the card grid, for every cell of the grid sets the id of a card (0 if there isn't a card)
     */
    public void doNotify(){
        int[][] cardGridMatrixUpdate = new int[maxRow][maxColumn];
        for (row = 0; row < maxRow; row++)
            for (column = 0; column < maxColumn; column++) {
                if(cardGridMatrix[row][column].size() != 0)
                    cardGridMatrixUpdate[row][column] = cardGridMatrix[row][column].peek().getId();
                else
                    cardGridMatrixUpdate[row][column] = 0;
            }
        notifyObservers(new CardGridUpdate(cardGridMatrixUpdate));
    }

    /**
     * Getter for testing purpose
     * @param color the color to consider
     * @return the amount of cards with a specific color
     */
    public int getNumOfCardsByColor(DevelopmentCardColor color) {
        int numOfCards = 0;
        for (row = 0; row < maxRow; row++) {
            for (column = 0; column < maxColumn; column++) {
                if (cardGridMatrix[row][column].peek().getColor().equals(color))
                    numOfCards = numOfCards + cardGridMatrix[row][column].size();
            }
        }
        return numOfCards;
    }
}