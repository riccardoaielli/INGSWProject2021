package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WareHouseDepotsCLI {
    private List<Map<Resource, Integer>> depots;
    private GetColorString color;

    public WareHouseDepotsCLI() {
        color = new GetColorString();
        depots = new ArrayList<>(3);
        //for(Map depot: depots)
        //    depot = new HashMap();
    }

    public void setDepots(List<Map<Resource, Integer>> depots) {
        this.depots = depots;
    }

    public void printWhareHouseDepots(){
        String line;
        int spaces = 0;
        HashMap<Resource,Integer> depot = new HashMap<>();
        System.out.println("DEPOTS: ");
        for(int i = 0; i < depots.size(); i++){
            switch (i + 1){
                case 1:
                    spaces = 1;
                    break;
                case 2:
                case 4:
                case 5:
                    spaces = 2;
                    break;
                case 3:
                    spaces = 3;
                    break;
            }

            depot = new HashMap<>(depots.get(i));
            line = "";
            if(depot.keySet().size() != 0) {
                for (Resource resource : depot.keySet()) {
                    for (int j = 0, printed = 0; j < spaces; j++) {
                        if (printed < depot.get(resource)) {
                            line = line.concat(color.getColorResource(resource) + "● " + cliColor.RESET);
                            printed++;
                        } else
                            line = line.concat("◌ ");
                    }
                }
            }
            else
                for(int emptySpace = 0;emptySpace<spaces;emptySpace++)
                    line = line.concat("◌ ");
            System.out.println(line);
        }
    }
}
