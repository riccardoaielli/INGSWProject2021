package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

public class Strongbox {
    private Map<Resource, Integer> strongbox;

    public void setStrongbox(Map<Resource, Integer> strongbox) {
        this.strongbox = strongbox;
    }

    public void printStrongbox(){

    }
}
