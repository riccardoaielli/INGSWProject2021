package it.polimi.ingsw.client.CLI.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that contains local information of the strongbox and the relatives methods to print information on a CLI
 */
public class StrongboxCLI {
    private Map<Resource, Integer> strongbox;
    private GetColorString color;
    private ArrayList<String> strongboxStrings;

    public StrongboxCLI() {
        color = new GetColorString();
        strongbox = new HashMap<>();
        strongbox.put(Resource.COIN,0);
        strongbox.put(Resource.STONE,0);
        strongbox.put(Resource.SERVANT,0);
        strongbox.put(Resource.SHIELD,0);
    }

    /**
     * Setter used to update the local strongbox
     * @param strongbox is the updated version of the strongbox
     */
    public void setStrongbox(Map<Resource, Integer> strongbox) {
        this.strongbox = strongbox;
    }

    /**
     * Method to print the strongbox
     */
    public void printStrongbox(){
        strongboxStrings = new ArrayList<>();
        String resource;
        resource = (color.getColorResource(Resource.COIN) + "●" + CliColor.RESET + ": " + strongbox.getOrDefault(Resource.COIN,0) + " ");
        if(strongbox.getOrDefault(Resource.COIN,0) < 10)
            resource = resource.concat(" ");

        resource = resource.concat(color.getColorResource(Resource.SERVANT) + "●" + CliColor.RESET + ": " + strongbox.getOrDefault(Resource.SERVANT, 0) + " ");
        if(strongbox.getOrDefault(Resource.SERVANT, 0) < 10)
            resource = resource.concat(" ");
        strongboxStrings.add(resource);

        resource = (color.getColorResource(Resource.SHIELD) + "●" + CliColor.RESET + ": " + strongbox.getOrDefault(Resource.SHIELD,0) + " ");
        if(strongbox.getOrDefault(Resource.SHIELD,0) < 10)
            resource = resource.concat(" ");

        resource = resource.concat(color.getColorResource(Resource.STONE) + "●" + CliColor.RESET + ": " + strongbox.getOrDefault(Resource.STONE,0) + " ");
        if(strongbox.getOrDefault(Resource.STONE,0) < 10)
            resource = resource.concat(" ");
        strongboxStrings.add(resource);
    }

    /**
     * Getter for a row of the strongbox
     * @param row the index of the row
     * @return the string of the row
     */
    public String getByRow(int row){
        return strongboxStrings.get(row);
    }
}
