package it.polimi.ingsw.client.CLI.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that contains local information of the strongbox and the relatives methods to print information on a CLI
 */
public class StrongboxCLI {
    private Map<Resource, Integer> strongbox;
    private GetColorString color;

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
        System.out.print("STRONGBOX[");
        strongbox.keySet().forEach(x -> System.out.print(color.getColorResource(x) + "●" + CliColor.RESET + ": " + strongbox.get(x) + " "));
        System.out.println("]");
    }
}
