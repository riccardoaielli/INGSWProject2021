package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class represents the characteristics of the development cards and the resources required for the activation of a leader card
 */
public class Requirement {
    private ArrayList<CardsRequirement> cardsRequirement;
    private HashMap<Resource,Integer> resourceRequirement;

    public Requirement(ArrayList<CardsRequirement> cardsRequirement, HashMap<Resource, Integer> resourceRequirement) {
        this.cardsRequirement = cardsRequirement;
        this.resourceRequirement = resourceRequirement;
    }

    public ArrayList<CardsRequirement> getCardsRequirement() {
        return cardsRequirement;
    }

    public HashMap<Resource, Integer> getResourceRequirement() {
        return resourceRequirement;
    }
}
