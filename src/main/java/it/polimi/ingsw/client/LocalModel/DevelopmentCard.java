package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.PowerOfProduction;
import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Map;

public class DevelopmentCard {
    private int id;
    private DevelopmentCardColor color;
    private int level;
    private Map<Resource,Integer> price;
    private int victoryPoints;
    private PowerOfProduction powerOfProduction;


}