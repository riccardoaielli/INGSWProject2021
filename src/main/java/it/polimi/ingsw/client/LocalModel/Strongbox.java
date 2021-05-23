package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Strongbox {
    private Map<Resource, Integer> strongbox;
    private GetColorString color;

    public Strongbox() {
        color = new GetColorString();
        strongbox = new HashMap<>();
    }

    public void setStrongbox(Map<Resource, Integer> strongbox) {
        this.strongbox = strongbox;
    }

    public void printStrongbox(){
        System.out.print("STRONGBOX[");
        strongbox.keySet().forEach(x -> System.out.print(color.getColorResource(x) + "●" + cliColor.RESET + " :" + strongbox.get(x) + " "));
        System.out.println("]");
    }
}
