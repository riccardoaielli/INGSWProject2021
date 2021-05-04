package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.enumerations.MatchPhase;
import it.polimi.ingsw.model.exceptions.InvalidNickName;
import it.polimi.ingsw.model.exceptions.InvalidParameterException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


public class Match {
    private int matchID;
    private final int numOfPlayers;
    private int numOfPlayersReady;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private Stack<LeaderCard> leaderCards;
    private MatchPhase matchPhase;
    private Market market;
    private CardGrid cardGrid;



    public Match(int matchID, int numOfPlayer) {
        this.matchID = matchID;
        this.numOfPlayers = numOfPlayer;
        numOfPlayersReady = 0;
        matchPhase = MatchPhase.SETUP;
        //deserialize leader cards from json file
        loadLeaderCards();
        //shuffle leader cards
        Collections.shuffle(leaderCards);
        //crates market and card grid
        market = new Market();
        cardGrid = new CardGrid();
        cardGrid.AddMatchToNotify(this);
    }

    /**
     * this method loads all the leader cards from the resources of the model
     */
    private void loadLeaderCards(){
        Gson gson = new Gson();
        String path = "src/main/java/it/polimi/ingsw/model/resources/leaderCards.json";

        Reader reader = null;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Type myDataType = new TypeToken<Stack<LeaderCard>>(){}.getType();
        leaderCards = gson.fromJson(reader, myDataType);
    }

    /**
     * This method is called when a player wants to join the match
     * @param nickName represents the nickname of the player that's joining the match
     * @throws InvalidNickName when the nickname requested already exist
     */
    public void addPlayer(String nickName) throws InvalidNickName {
        //checks if the nickname is not taken in this match
        if(players.stream().noneMatch(x -> x.getNickname().equals(nickName))) {
            //draw 4 cards from the leader cards stack
            ArrayList drawedLeaderCards = new ArrayList();
            for(int draws = 0; draws < 4; draws++)
                drawedLeaderCards.add(leaderCards.pop());
            //creates the player and its personal board
            players.add(new Player(nickName, new PersonalBoard(drawedLeaderCards, this)));
            numOfPlayersReady++;
            //when ready players reaches the number of players for the match the game starts
            if (numOfPlayers == numOfPlayersReady)
                matchPhase = MatchPhase.STANDARDROUND;
        }
        else
            throw new InvalidNickName();
    }

    public Market getMarket() {
        return market;
    }

    public CardGrid getCardGrid() {
        return cardGrid;
    }

    public void setup(){

    }

    public void endTurn(){

    }

    public void nextPlayer(){
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method activates the vatican report on all players on a specific tile
     * @param tileNumber is the tile to activate
     */
    public void vaticanReport(int tileNumber){
        players.forEach(x -> x.getPersonalBoard().activateVaticanReport(tileNumber));
    }

    /**
     * This method is called by the faithTracks and by the developmentCardSpaces when they reaches the condition to end the game
     */
    public void setLastRound() {
        if(matchPhase == MatchPhase.STANDARDROUND)
            matchPhase = MatchPhase.LASTROUND;
    }

    public void endGame() {

    }

    /**
     * This method moves the faith marker for all the players out of the current player
     * @param positions is the number of steps to make on the faith track for each player
     */
    public void moveFaithMarkerAll(int positions){
        players.stream().filter(x -> x != currentPlayer).forEach(x -> {
            try {
                x.getPersonalBoard().moveFaithMarker(positions);
            } catch (InvalidParameterException invalidParameterException) {
                invalidParameterException.printStackTrace();
            }
        });
        players.stream().filter(x -> x != currentPlayer).forEach(x -> x.getPersonalBoard().checkVaticanReport());
    }

}
