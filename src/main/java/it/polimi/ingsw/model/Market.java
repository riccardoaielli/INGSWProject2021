package it.polimi.ingsw.model;

import java.util.*;

/**
 * this class represents the market
 */
public class Market {

    private Marble[][] marketMatrix = new Marble[3][4];
    private Marble marbleOut, tempMarble;

    private final int maxRow = 3;
    private final int maxColumn = 4;

    /**
     * this constructor creates the market data structure and initializes it with randomly placed marbles
     */
    public Market(){

        int i, j;
        Stack<Marble> marbleStack = new Stack<>();

        // filling the stack with all needed marbles
        for (i=0; i<4 ; i++){
            marbleStack.push(WhiteMarble.getInstance());
        }

        for (i=0; i<2 ; i++){
            marbleStack.push(BlueMarble.getInstance());
            marbleStack.push(YellowMarble.getInstance());
            marbleStack.push(GreyMarble.getInstance());
            marbleStack.push(PurpleMarble.getInstance());
        }
        marbleStack.push(RedMarble.getInstance());

        // randomizing marbleStack
        Collections.shuffle(marbleStack);

        // filling the marketMatrix randomly with marbles
        for(i=0; i<maxRow; i++)
            for(j=0; j<maxColumn; j++){
                marketMatrix[i][j] = marbleStack.pop();
            }

        marbleOut = marbleStack.pop();

    }

    // this method changes the marketMatrix data structure
    private void changeMarket(int rowOrColumn, int value){

        int row, column;

        if(rowOrColumn==0){
            tempMarble = marbleOut;
            marbleOut = marketMatrix[value][0];
            for (row=value, column=0; column<3 ; column++) {
                marketMatrix[row][column] = marketMatrix[row][column+1];
            }
            marketMatrix[row][column] = tempMarble;
        }
        if(rowOrColumn==1){
            tempMarble = marbleOut;
            marbleOut = marketMatrix[0][value];
            for (row=0, column=value; row<2 ; row++) {
                marketMatrix[row][column] = marketMatrix[row+1][column];
            }
            marketMatrix[row][column] = tempMarble;
        }
    }

    /**
     * this method returns a map of marbles according to the input parameters
     */
    public Map<Marble,Integer> takeBoughtMarbles(int rowOrColumn, int value){

        int row, column;
        Map<Marble,Integer> temporaryMapMarble = new HashMap<>();

        if(rowOrColumn==0){
            for (row=value, column=0; column<maxColumn ; column++) {
                temporaryMapMarble.merge(marketMatrix[row][column], 1, Integer::sum); //adding marbles from the matrix to the map
            }
        }
        if(rowOrColumn==1){
            for (row=0, column=value; row<maxRow ; row++) {
                temporaryMapMarble.merge(marketMatrix[row][column], 1, Integer::sum);
            }
        }

        changeMarket(rowOrColumn, value);
        return temporaryMapMarble;
    }

    public Marble[][] getMarketMatrix() {
        return marketMatrix;
    }

    public Marble getMarbleOut() {
        return marbleOut;
    }

}
