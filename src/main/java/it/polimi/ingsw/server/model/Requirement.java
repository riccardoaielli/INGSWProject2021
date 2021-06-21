package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.Map;

/**
 * this class represents the characteristics of the development cards and the resources required for the activation of a leader card
 */
public class Requirement {
    private ArrayList<CardRequirement> cardRequirement;
    private Map<Resource,Integer> resourceRequirement;

    public Requirement(ArrayList<CardRequirement> cardRequirement, Map<Resource, Integer> resourceRequirement) {
        this.cardRequirement = cardRequirement;
        this.resourceRequirement = resourceRequirement;
    }

    /**
     * Getter for the cards required to activate the leader card
     * @return an arraylist of cardrequirements
     */
    public ArrayList<CardRequirement> getCardsRequirement() {
        return cardRequirement;
    }

    /**
     * Getter for the resources required to activate the leader card
     * @return a map with the resources quantities required
     */
    public Map<Resource, Integer> getResourceRequirement() {
        return resourceRequirement;
    }
}
