package it.polimi.ingsw.client.CLI.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that contains local information of the warehouse depot and the relatives methods to print information on a CLI
 */
public class WareHouseDepotsCLI {
    private final int NUM_OF_DEPOTS = 3;
    private List<Map<Resource, Integer>> depots;
    private GetColorString color;

    public WareHouseDepotsCLI() {
        color = new GetColorString();
        depots = new ArrayList<>(NUM_OF_DEPOTS);
        for (int depot = 0; depot < NUM_OF_DEPOTS; depot++)
            depots.add(new HashMap<>());
    }

    /**
     * Setter used to update the local warehouse
     * @param depots is the updated version of the warehouse
     */
    public void setDepots(List<Map<Resource, Integer>> depots) {
        this.depots = depots;
    }

    /**
     * Method to print the warehouse depot
     */
    public void printWhareHouseDepots(){
        String line = "";
        int spaces = 0;
        HashMap<Resource,Integer> depot = new HashMap<>();
        System.out.println("DEPOTS: ");
        for(int i = 0; i < depots.size(); i++){
            switch (i + 1){
                case 1:
                    spaces = 1;
                    line = "  ";
                    break;
                case 2:
                    spaces = 2;
                    line = " ";
                    break;
                case 4:
                case 5:
                    spaces = 2;
                    line = "";
                    break;
                case 3:
                    spaces = 3;
                    line = "";
                    break;
            }

            depot = new HashMap<>(depots.get(i));
            if(depot.keySet().size() != 0) {
                for (Resource resource : depot.keySet()) {
                    for (int j = 0, printed = 0; j < spaces; j++) {
                        if (printed < depot.get(resource)) {
                            line = line.concat(color.getColorResource(resource) + "● " + CliColor.RESET);
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
