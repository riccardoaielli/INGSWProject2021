package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.List;
import java.util.Map;

public class WareHouseDepots {
    private List<Map<Resource, Integer>> depots;

    public void setDepots(List<Map<Resource, Integer>> depots) {
        this.depots = depots;
    }
    public void printWhareHouseDepots(){

    }
}
