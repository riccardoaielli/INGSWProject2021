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
        resource = (color.getColorResource(Resource.COIN) + "●" + CliColor.RESET + ": " + strongbox.get(Resource.COIN) + " ");
        if(strongbox.get(Resource.COIN) < 10)
            resource = resource.concat(" ");
        resource = resource.concat(color.getColorResource(Resource.SERVANT) + "●" + CliColor.RESET + ": " + strongbox.get(Resource.SERVANT) + " ");
        if(strongbox.get(Resource.SERVANT) < 10)
            resource = resource.concat(" ");
        strongboxStrings.add(resource);
        resource = (color.getColorResource(Resource.SHIELD) + "●" + CliColor.RESET + ": " + strongbox.get(Resource.SHIELD) + " ");
        if(strongbox.get(Resource.SHIELD) < 10)
            resource = resource.concat(" ");
        resource = resource.concat(color.getColorResource(Resource.STONE) + "●" + CliColor.RESET + ": " + strongbox.get(Resource.STONE) + " ");
        if(strongbox.get(Resource.STONE) < 10)
            resource = resource.concat(" ");
        strongboxStrings.add(resource);
    }

    public String getByRow(int row){
        return strongboxStrings.get(row);
    }
}
