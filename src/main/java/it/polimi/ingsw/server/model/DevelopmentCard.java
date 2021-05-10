package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;

import java.util.HashMap;

/**
 * This class represent a card that players can buy from the market
 */
public class DevelopmentCard {

    private int id;
    private DevelopmentCardColor color;
    private int level;
    private HashMap<Resource,Integer> price;
    private int victoryPoints;
    private PowerOfProduction powerOfProduction;

    /**
     *
     * @param color represents the color of the card
     * @param level represents the level of the card
     * @param price represents the resources needed to buy the card from cardGrid
     * @param victoryPoints represents the victory points that the card values at the and of the game
     * @param powerOfProduction represents the production available for the card owner
     */
    public DevelopmentCard(DevelopmentCardColor color, int level, HashMap<Resource, Integer> price, int victoryPoints, PowerOfProduction powerOfProduction) throws InvalidParameterException {
        if((level > 0 && level < 4 && victoryPoints > 0) || (price != null && powerOfProduction != null)){
            this.color = color;
            this.level = level;
            this.price = price;
            this.victoryPoints = victoryPoints;
            this.powerOfProduction = powerOfProduction;
        }
        else
            throw new InvalidParameterException();
    }

    /**
     * @return the id of the card
     */
    public int getId() {
        return id;
    }

    /**
     * @return the color of the card
     */
    public DevelopmentCardColor getColor() {
        return color;
    }

    /**
     * @return the resources necessary to buy the card from the cardGrid
     */
    public HashMap<Resource, Integer> getPrice() {
        return price;
    }

    /**
     * @return the level of the card
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the victory points of the card
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * @return the powerOfProduction associated at the card
     */
    public PowerOfProduction getPowerOfProduction() {
        return powerOfProduction;
    }
}
