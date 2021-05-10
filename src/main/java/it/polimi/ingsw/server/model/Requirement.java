package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class represents the characteristics of the development cards and the resources required for the activation of a leader card
 */
public class Requirement {
    private ArrayList<CardRequirement> cardRequirement;
    private HashMap<Resource,Integer> resourceRequirement;

    public Requirement(ArrayList<CardRequirement> cardRequirement, HashMap<Resource, Integer> resourceRequirement) {
        this.cardRequirement = cardRequirement;
        this.resourceRequirement = resourceRequirement;
    }

    public ArrayList<CardRequirement> getCardsRequirement() {
        return cardRequirement;
    }

    public HashMap<Resource, Integer> getResourceRequirement() {
        return resourceRequirement;
    }
}
