package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;

public class FaithTrack {
    private int redcrossPosition;
    private ArrayList<Integer> popeFavourTiles;
    private GetColorString getColorString = new GetColorString();

    public FaithTrack() {
        redcrossPosition = 0;
        popeFavourTiles = new ArrayList<>();
        popeFavourTiles.add(0);
        popeFavourTiles.add(0);
        popeFavourTiles.add(0);
    }

    public void setRedcrossPosition(int redcrossPosition) {
        this.redcrossPosition = redcrossPosition;
    }

    public void setPopeFavourTiles(ArrayList<Integer> popeFavourTiles) {
        this.popeFavourTiles = popeFavourTiles;
    }

    public void printFaithTrack(){

        String cell = "|";
        String redCross = getColorString.getColorResource(Resource.FAITH)+"┼"+cliColor.RESET;
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
                case 1:
                    tile = "└-------y------┘";
                case 2:
                    tile = "└----------z------┘";
            }
            switch (popeFavourTiles.get(i)){
                case 1:
                    popeFavourTilesString = popeFavourTilesString.replace(tile,"\\u001b[33m"+tile+"\\u001b[0m");
                case 2:
                    popeFavourTilesString = popeFavourTilesString.replace(tile,"\\u001b[32m"+tile+"\\u001b[0m");
            }
        }
        popeFavourTilesString = popeFavourTilesString.replace("x","2");
        popeFavourTilesString = popeFavourTilesString.replace("y","2");
        popeFavourTilesString = popeFavourTilesString.replace("z","4");

        String outString = faithTrack.concat("\n").concat(popeFavourTilesString);
        System.out.println(outString);
    }
}
