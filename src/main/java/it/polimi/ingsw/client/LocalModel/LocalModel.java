package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Marble;

import java.util.ArrayList;

/***
 * This class contains a local model with the necessary information to update the CLI or the GUI
 */
public class LocalModel {
    private LocalPhase phase;
    private ArrayList <Player> players;
    private String localPlayer;
    private String currentPlayer;
    private Market market;
    private CardGrid cardGrid;
    private FaithTrack faithTrack;
    private DevelopmentCardSpace developmentCardSpace;
    private WareHouseDepots wareHouseDepots;
    private Strongbox strongbox;

    public LocalModel() {
        players = new ArrayList<>();
        phase = LocalPhase.DEFAULT;
    }

    public LocalPhase getPhase() {
        return phase;
    }

    public void setPhase(LocalPhase phase) {
        this.phase = phase;
    }

    public void addPlayer(String Nickname){

    }

    /**
     * This method updates the local model with the market structure received from a model update or a market update message
     * @param marketMatrix is a matrix that represents the market
     * @param marbleOut is the marble out of the market
     */
    public void setMarket(Marble[][] marketMatrix,Marble marbleOut){

    }

    /**
     * This method updates the local model with the cardGrid structure received from a model update or a cardGrid update message
     * @param cardGridMatrix is a matrix that represents the cardGrid, each position refers to a development card ID
     */
    public void setCardGrid(int[][] cardGridMatrix){

    }

    /**
     * this method updates the first four cards that the player receives at the beginning of the game
     * @param initialLeaderCards a list of cards
     */
    public void setInitialLeaderCards(ArrayList<Integer> initialLeaderCards){
        players.stream().filter(x->x.getNickname().equals(localPlayer)).forEach(x->x.setLeaderCards(initialLeaderCards));
    }


    public void getPlayer(String Nickname){

    }



    public void printMarket(){
        System.out.println(market);

    }
    public void printCardGrid(){
        System.out.println(cardGrid);
    }


    public void printView(){

    }

    public void printLeaderCards() {
        System.out.println(localPlayer + "stampare carte leader del giocatore locale");
    }

    public void setLocalPlayer(String localPlayer) {
        this.localPlayer = localPlayer;
        players.add(new Player(localPlayer));
    }
}
