package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Marble;

public class MarketCLI {
    private  Marble[][] marketMatrix;
    private  Marble marbleOut;
    private GetColorString getColorString = new GetColorString();

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
                System.out.print( getColorString.getColorMarble(marketMatrix[i][j]) + "● ");
            }
            System.out.print( cliColor.RESET + "" + (i+1) + "\n");
        }
        for(j=0; j<maxColumn; j++)
            System.out.print( cliColor.RESET + "" + (j+1) + " ");
        System.out.print( cliColor.RESET + "\nMarble out: " + getColorString.getColorMarble(marbleOut) + "● \n" + cliColor.RESET);
    }

}
