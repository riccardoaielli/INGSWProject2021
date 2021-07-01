package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.messages.messagesToClient.DevCardSpaceUpdate;
import it.polimi.ingsw.common.utils.observe.MessageObservable;
import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.server.model.exceptions.InvalidDevelopmentCardException;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;


import java.util.ArrayList;

/**
 * This class represents the slots of development cards that each player has on its personal board
 */
public class DevelopmentCardSpace extends MessageObservable implements ObservableGameEnder<EndGameConditionsObserver> {
    //Each of the three stack of cards is stored in an ArrayList and they are collected in another ArrayList
    private ArrayList<ArrayList<DevelopmentCard>> cards;
    private final int numOfStacks = 3;
    private int numOfcards;
    private EndGameConditionsObserver matchToNotify;


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

    //Private method to notify observers with the updated state of development card space
    private void doNotify(){
        ArrayList<ArrayList<Integer>> cardsState = new ArrayList<ArrayList<Integer>>();
        for (ArrayList<DevelopmentCard> developmentCardArrayList : cards){
            ArrayList<Integer> devCardIdArray = new ArrayList<>();
            for (DevelopmentCard developmentCard : developmentCardArrayList){
                devCardIdArray.add(developmentCard.getId());
            }
            cardsState.add(devCardIdArray);
        }
        notifyObservers(new DevCardSpaceUpdate(this.getNickname(), cardsState));
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

            if (card.getLevel() != 1 && stackList.size() == 0) {
                throw new InvalidDevelopmentCardException((("A card level " + card.getLevel() + " must be put on a level " + (card.getLevel() - 1)).replace("a level 0", "an empty space")));
            }else if ((card.getLevel() == 1 && stackList.size() == 0) || (card.getLevel() != 1 && (card.getLevel() - 1) == stackList.get(stackList.size() - 1).getLevel())) {
                stackList.add(card);
                numOfcards++;
            } else {
                throw new InvalidDevelopmentCardException((("A card level " + card.getLevel() + " must be put on a level " + (card.getLevel()-1)).replace("a level 0","an empty space")));
            }


        }
        else{
            throw new InvalidParameterException("The position is not valid or the card doesn't exist");
        }
        doNotify();
        //notifies match when reaches 7 cards in the development card space
        if(numOfcards == 7 && matchToNotify != null)
            matchToNotify.update(false);
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
     * this method check if a card in the card space matches a combination of level and color, the level is 0 when it is not requested a specific level
     * @param color is the color to match
     * @param level is the level to match
     * @return true if it finds a match
     */
    private boolean checkSingleRequirement(DevelopmentCardColor color, Integer level){
        int stack = 0;
        ArrayList<DevelopmentCard> singleStack;

        while(stack < cards.size()){
            singleStack = cards.get(stack);
            if(level != 0 && singleStack.stream().filter(x -> x.getColor() == color && x.getLevel() == level).count() > 0)
                return true;
            if(level == 0 && singleStack.stream().filter(x -> x.getColor() == color).count() > 0)
                return true;
            stack++;
        }
        return false;
    }

    /**
     * @param cardPosition represents the number of the stack: 1,2 or 3 from left to right
     * @return the power of production requested in cardPosition
     * @throws InvalidParameterException when the cardPosition is not valid
     */
    public PowerOfProduction getPowerOfProduction(int cardPosition) throws InvalidParameterException{
        cardPosition = cardPosition - 1;
        if(cardPosition >= 0 && cardPosition <  numOfStacks && cards.get(cardPosition) != null && cards.get(cardPosition).size() != 0) {
            ArrayList<DevelopmentCard> cardStack = cards.get(cardPosition);
            return cardStack.get(cardStack.size() - 1).getPowerOfProduction();
        }
        else
            throw new InvalidParameterException("The position is not valid or the card doesn't exist");
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

    /**
     * This method sets the match to notify and is called after the constructor of the class
     * @param observer is the match that this class has to notify when reaches 7 cards
     */
    @Override
    public void addObserver(EndGameConditionsObserver observer) {
        this.matchToNotify = observer;
    }
}
