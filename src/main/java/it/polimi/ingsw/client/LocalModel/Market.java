package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Marble;

public class Market {
    private  Marble[][] marketMatrix;
    private  Marble marbleOut;

    private final int maxRow = 3;
    private final int maxColumn = 4;
    int i, j;

    public void setMarketMatrix(Marble[][] marketMatrix) {
        this.marketMatrix = marketMatrix;
    }

    public void setMarbleOut(Marble marbleOut) {
        this.marbleOut = marbleOut;
    }

    public void printMarket(){

        for(i=0; i<maxRow; i++) {
            for (j = 0; j < maxColumn; j++){
                System.out.print( getColor(marketMatrix[i][j]) + "● ");
            }
            System.out.print( cliColor.RESET + "" + i + "\n");
        }
        for(j=0; j<maxColumn; j++)
            System.out.print( cliColor.RESET + "" + j + " ");
        System.out.print( cliColor.RESET + "\nMarble out: " + getColor(marbleOut) + "● \n");
    }

    public cliColor getColor(Marble marble) {

        switch (marble) {
            case YELLOWMARBLE:
                return cliColor.COLOR_YELLOW;
            case GREYMARBLE:
                return cliColor.COLOR_GREY;
            case REDMARBLE:
                return cliColor.COLOR_RED;
            case BLUEMARBLE:
                return cliColor.COLOR_BLUE;
            case PURPLEMARBLE:
                return cliColor.COLOR_PURPLE;
            case WHITEMARBLE:
                return cliColor.COLOR_WHITE;
        }
        return null;
    }

}
