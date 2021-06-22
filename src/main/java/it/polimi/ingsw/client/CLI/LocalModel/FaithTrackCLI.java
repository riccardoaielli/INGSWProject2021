package it.polimi.ingsw.client.CLI.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;

/**
 * Class that contains local information of the faith track and the relatives methods to print information on a CLI
 */
public class FaithTrackCLI {
    private int redcrossPosition;
    private ArrayList<Integer> popeFavourTiles;
    private GetColorString getColorString = new GetColorString();

    public FaithTrackCLI() {
        redcrossPosition = 0;
        popeFavourTiles = new ArrayList<>();
        popeFavourTiles.add(0);
        popeFavourTiles.add(0);
        popeFavourTiles.add(0);
    }

    /**
     * Setter used to update the position of the red cross in the faith track
     * @param redcrossPosition is the updated position
     */
    public void setRedcrossPosition(int redcrossPosition) {
        this.redcrossPosition = redcrossPosition;
    }

    /**
     * Setter used to update the favour tiles of the faith track
     * @param popeFavourTiles are the favour tiles updated
     */
    public void setPopeFavourTiles(ArrayList<Integer> popeFavourTiles) {
        this.popeFavourTiles = popeFavourTiles;
    }

    /**
     * Method used to print the faith track
     */
    public void printFaithTrack(){
        String cell = "|";
        String redCross = getColorString.getColorResource(Resource.FAITH)+"┼"+ CliColor.RESET;
        String faithTrack = "";
        String popeFavourTilesString = "         └---x---┘       └-------y------┘     └----------z------┘";
        for (int pos = 0; pos<=24; pos++){
            if(redcrossPosition == pos)
                faithTrack = faithTrack.concat(redCross).concat(cell);
            else
                faithTrack = faithTrack.concat(String.valueOf(pos)).concat(cell);
        }

        String tile = "";
        for (int i = 0; i < popeFavourTiles.size(); i++){
            switch (i){
                case 0:
                    tile = "└---x---┘";
                    break;
                case 1:
                    tile = "└-------y------┘";
                    break;
                case 2:
                    tile = "└----------z------┘";
                    break;
            }
            switch (popeFavourTiles.get(i)){
                case 1:
                    popeFavourTilesString = popeFavourTilesString.replace(tile, CliColor.COLOR_RED+tile+ CliColor.RESET);
                    break;
                case 2:
                    popeFavourTilesString = popeFavourTilesString.replace(tile, CliColor.COLOR_YELLOW+tile+ CliColor.RESET);
                    break;
            }
        }
        popeFavourTilesString = popeFavourTilesString.replace("x","2");
        popeFavourTilesString = popeFavourTilesString.replace("y","2");
        popeFavourTilesString = popeFavourTilesString.replace("z","4");

        String outString = faithTrack.concat("\n").concat(popeFavourTilesString);
        System.out.println(outString);
    }
}
