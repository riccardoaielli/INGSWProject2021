package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.model.exceptions.InvalidDevelopmentCardException;
import it.polimi.ingsw.model.exceptions.InvalidParameterException;


import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class represents the slots of development cards that each player has on its personal board
 */
public class DevelopmentCardSpace {
    //Each of the three stack of cards is stored in an ArrayList and they are collected in another ArrayList
    private ArrayList<ArrayList<DevelopmentCard>> cards;
    private final int numOfStacks = 3;
    private int numOfcards;

    /**
     * the constructor generates an empty structure for the development cards
     */
    public DevelopmentCardSpace() {
        cards = new ArrayList<>();
        for(int i = 0; i < numOfStacks; i++){
            cards.add(new ArrayList<>());
        }
        numOfcards = 0;
    }



    /**
     * this method adds a card on top of a specified stack.
     * level 1 cards must be placed in an empty stack
     * level 2 cards must be placed on a level 1 card
     * level 3 cards must be placed on a level 2 card
     * @param card represents the card to add
     * @param cardPosition represents the number of the stack: 1,2 or 3 from left to right
     */
    public void addCard(DevelopmentCard card, int cardPosition) throws InvalidDevelopmentCardException, InvalidParameterException {
        cardPosition = cardPosition - 1;
        if((card != null) || (cardPosition >= 0 && cardPosition < numOfStacks)) {
            ArrayList<DevelopmentCard> stackList = cards.get(cardPosition);

            if ((card.getLevel() == 1 && stackList.size() == 0) || (card.getLevel() != 1 && (card.getLevel() - 1) == stackList.get(stackList.size() - 1).getLevel())) {
                stackList.add(card);
                numOfcards++;
            } else {
                throw new InvalidDevelopmentCardException();
            }
        }
        else{
            throw new InvalidParameterException();
        }
        //notifies match when reaches 7 cards in the development card space
    }


    /**
     * this method checks if a leaderCard's requirement matches a card contained in this developmentCard space
     * @param requirements are the requirements for the activation of a leader card
     * @return true if it matches the requirements with cards from the card space
     */
    public boolean checkRequirement(ArrayList<CardRequirement> requirements){
       if(requirements.stream().filter(x -> checkSingleRequirement(x.getColor(),x.getLevel()) == false).count() == 0)
           return true;
       else
           return false;
    }

    /**
     * this method check if a card in the card space matches a combination of level and color, the level can be null
     * @param color is the color to match
     * @param level is the level to match
     * @return true if it finds a match
     */
    private boolean checkSingleRequirement(DevelopmentCardColor color, Integer level){
        int stack = 0;
        ArrayList<DevelopmentCard> singleStack;

        while(stack < cards.size()){
            singleStack = cards.get(stack);
            if(level != null && singleStack.stream().filter(x -> x.getColor() == color && x.getLevel() == level).count() > 0)
                return true;
            if(level == null && singleStack.stream().filter(x -> x.getColor() == color).count() > 0)
                return true;
            stack++;
        }
        return false;
    }

    /**
     * @return an ArrayList that contains all the power of production available for the player
     */
    public ArrayList<PowerOfProduction> getPowerOfProduction(){
        int stack = 0;
        ArrayList<PowerOfProduction> powerOfProductions = new ArrayList<>();
        ArrayList<DevelopmentCard> stackList;
        while (stack < cards.size()){
            stackList = cards.get(stack);
            if(stackList.size() != 0)
                powerOfProductions.add(stackList.get(stackList.size() - 1).getPowerOfProduction());
            stack++;
        }
        return powerOfProductions;
    }

    /**
     * @return victory points earned from all the cards own from the player
     */
    public int getVictoryPoints(){
        int totalVictoryPoints = 0;
        int stack = 0;

        while(stack < cards.size()){
            totalVictoryPoints = totalVictoryPoints + cards.get(stack).stream().mapToInt(x -> x.getVictoryPoints()).sum();
            stack++;
        }
        return totalVictoryPoints;
    }

    /**
     * this method is implemented for testing purpose
     * @param cardPosition represents the number of the stack: 1,2 or 3 from left to right
     * @return the last card added in cardPosition
     */
    public DevelopmentCard getCard(int cardPosition){
        cardPosition = cardPosition - 1;
        assert cardPosition >= 0 && cardPosition < 3;
        ArrayList<DevelopmentCard> stack = cards.get(cardPosition);
        return stack.get(stack.size() - 1);
    }

}
