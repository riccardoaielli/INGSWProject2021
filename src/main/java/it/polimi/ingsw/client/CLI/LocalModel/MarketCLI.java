package it.polimi.ingsw.client.CLI.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Marble;

/**
 *Class that contains local information of the market and the relatives methods to print information on a CLI
 */
public class MarketCLI {
    private  Marble[][] marketMatrix;
    private  Marble marbleOut;
    private GetColorString getColorString = new GetColorString();

    private final int maxRow = 3;
    private final int maxColumn = 4;
    int i, j;

    /**
     * Setter used to update the marbles inside of the market
     * @param marketMatrix is the updated version of the market
     */
    public void setMarketMatrix(Marble[][] marketMatrix) {
        this.marketMatrix = marketMatrix;
    }

    /**
     * Setter used to update the marble out of the market
     * @param marbleOut is the updated marble
     */
    public void setMarbleOut(Marble marbleOut) {
        this.marbleOut = marbleOut;
    }

    /**
     * Method used to print the market
     */
    public void printMarket(){

        for(i=0; i<maxRow; i++) {
            for (j = 0; j < maxColumn; j++){
                System.out.print( getColorString.getColorMarble(marketMatrix[i][j]) + "● ");
            }
            System.out.print( CliColor.RESET + "" + (i+1) + "\n");
        }
        for(j=0; j<maxColumn; j++)
            System.out.print( CliColor.RESET + "" + (j+1) + " ");
        System.out.print( CliColor.RESET + "\nMarble out: " + getColorString.getColorMarble(marbleOut) + "● \n" + CliColor.RESET);
    }

}
