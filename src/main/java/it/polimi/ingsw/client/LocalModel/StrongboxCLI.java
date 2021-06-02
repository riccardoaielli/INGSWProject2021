package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    public void setStrongbox(Map<Resource, Integer> strongbox) {
        this.strongbox = strongbox;
    }

    public void printStrongbox(){
        System.out.print("STRONGBOX[");
        strongbox.keySet().forEach(x -> System.out.print(color.getColorResource(x) + "●" + cliColor.RESET + ": " + strongbox.get(x) + " "));
        System.out.println("]");
    }
}
