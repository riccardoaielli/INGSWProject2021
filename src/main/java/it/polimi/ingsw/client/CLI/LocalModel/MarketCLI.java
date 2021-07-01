package it.polimi.ingsw.client.CLI.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Marble;

import java.util.ArrayList;

/**
 *Class that contains local information of the market and the relatives methods to print information on a CLI
 */
public class MarketCLI {
    private  Marble[][] marketMatrix;
    private  Marble marbleOut;
    private GetColorString getColorString = new GetColorString();
    private ArrayList<String> marketStrings;
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
        marketStrings = new ArrayList<>();
        String row = "";
        for(i=0; i<maxRow; i++) {
            row = "";
            for (j = 0; j < maxColumn; j++){
                row = row.concat(getColorString.getColorMarble(marketMatrix[i][j]) + "● ");
                //System.out.print( getColorString.getColorMarble(marketMatrix[i][j]) + "● ");
            }
            row = row.concat(CliColor.RESET + "" + (i+1) );
            marketStrings.add(row);
            //System.out.print( CliColor.RESET + "" + (i+1) + "\n");
        }
        row = "";
        for(j=0; j<maxColumn; j++)
            row = row.concat(CliColor.RESET + "" + (j+1) + " ");
            //System.out.print( CliColor.RESET + "" + (j+1) + " ");
        row = row.concat( CliColor.RESET + " Marble out: " + getColorString.getColorMarble(marbleOut) + "● " + CliColor.RESET);
        marketStrings.add(row);
        //System.out.print( CliColor.RESET + "\nMarble out: " + getColorString.getColorMarble(marbleOut) + "● \n" + CliColor.RESET);
    }

    /**
     * Getter for a row of the market
     * @param row the index of the row
     * @return the string of the row
     */
    public String getByRow(int row){
        return marketStrings.get(row);
    }
}
