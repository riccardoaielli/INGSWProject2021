package it.polimi.ingsw.server.model;

import java.util.*;

/**
 * This class represents all the logic under the single player game
 */
public class Lorenzo {

    private int faithTrackPositionBlack = 0;
    private Stack<SoloActionToken> soloActionTokenStack;
    private Stack<SoloActionToken> soloActionTokenUsedStack;
    private SoloActionToken currentActionToken;
    private CardGrid cardGrid;


    /**
     * this constructor creates the soloActionTokenStack and initializes it with randomly placed token
     */
    public Lorenzo(CardGrid cardGrid){

        this.cardGrid = cardGrid;
        soloActionTokenStack = new Stack<>();
        soloActionTokenUsedStack = new Stack<>();

        soloActionTokenStack.push(new YellowToken(cardGrid));
        soloActionTokenStack.push(new GreenToken(cardGrid));
        soloActionTokenStack.push(new BlueToken(cardGrid));
        soloActionTokenStack.push(new PurpleToken(cardGrid));
        soloActionTokenStack.push(new BlackCrossOne(this));
        soloActionTokenStack.push(new BlackCrossTwo(this));
        soloActionTokenStack.push(new BlackCrossTwo(this));

        Collections.shuffle(soloActionTokenStack);

    }


    /**
     * this method moves the black cross
     * @param numOfSteps represents the amount of movements that the black cross has to do on the faith track
     */
    public void moveFaithMarker(int numOfSteps){
        assert  numOfSteps >= 0;
        faithTrackPositionBlack = faithTrackPositionBlack + numOfSteps;
        if (faithTrackPositionBlack > 20)        // the maximum amount of space in the track is 20
            faithTrackPositionBlack = 20;
    }

    /**
     * this method returns the position in the faith track
     * @return an int representing the position of the black cross in the faith track
     */
    public int getFaithTrackPositionBlack(){
        return faithTrackPositionBlack;
    }

    /**
     * this method resets soloActionTokenStack and shuffles it
     */
    public void shuffle(){

        while(!soloActionTokenUsedStack.empty()){
            currentActionToken = soloActionTokenUsedStack.pop();
            soloActionTokenStack.push(currentActionToken);
        }
        Collections.shuffle(soloActionTokenStack);
    }

    /**
     * this method draws a soloActionToken and calls the soloAction of the extracted token
     */
    public void draw() {
        currentActionToken = soloActionTokenStack.pop();
        soloActionTokenUsedStack.push(currentActionToken);
        currentActionToken.soloAction();
    }

}
