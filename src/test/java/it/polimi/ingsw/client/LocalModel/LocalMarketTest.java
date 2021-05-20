package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Marble;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class LocalMarketTest {

    private final int maxRow = 3;
    private final int maxColumn = 4;
    private Marble[][] marketMatrix = new Marble[3][4];
    private Marble marbleOut, tempMarble;
    private Stack<Marble> marbleStack;

    @Test
    public void printMarket() {

        marbleStack = new Stack<>();

        // filling the stack with all needed marbles
        for (int i=0; i< 4; i++){
            marbleStack.push(Marble.WHITEMARBLE);
        }

        for (int i=0; i<2 ; i++){
            marbleStack.push(Marble.BLUEMARBLE);
            marbleStack.push(Marble.YELLOWMARBLE);
            marbleStack.push(Marble.GREYMARBLE);
            marbleStack.push(Marble.PURPLEMARBLE);
        }
        marbleStack.push(Marble.REDMARBLE);

        // randomizing marbleStack
        Collections.shuffle(marbleStack);

        // filling the marketMatrix randomly with marbles
        for(int i=0; i<maxRow; i++)
            for(int j=0; j<maxColumn; j++){
                marketMatrix[i][j] = marbleStack.pop();
            }

        marbleOut = marbleStack.pop();
        Market market = new Market();
        market.setMarketMatrix(marketMatrix);
        market.setMarbleOut(marbleOut);

        market.printMarket();
    }
}