package it.polimi.ingsw.server.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.common.messages.messagesToClient.PlayerTurnUpdate;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.utils.observe.MessageObservable;
import it.polimi.ingsw.server.model.enumerations.MatchPhase;
import it.polimi.ingsw.server.model.exceptions.InvalidNickName;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


public class Match extends MessageObservable implements EndGameConditionsObserver {
    private int matchID;
    private final int numOfPlayers;
    private int numOfPlayersReady;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private Stack<LeaderCard> leaderCards;
    private Stack<LeaderCard> leaderCardDepot;
    private Stack<LeaderCard> leaderCardMarble;
    private Stack<LeaderCard> leaderCardDiscount;
    private Stack<LeaderCard> leaderCardProduction;
    private MatchPhase matchPhase;
    private Market market;
    private CardGrid cardGrid;
    private ArrayList<Player> rank;

    /**
     * This constructor creates a match deserializing all the leader cards and creating the market and the cardgrind
     * @param matchID an int that identifies the match
     * @param numOfPlayer the number of players that will join the match
     */
    public Match(int matchID, int numOfPlayer) throws InvalidParameterException {
        if (numOfPlayer < 0 || numOfPlayer > 4)
            throw new InvalidParameterException();
        this.matchID = matchID;
        this.numOfPlayers = numOfPlayer;
        numOfPlayersReady = 0;
        matchPhase = MatchPhase.SETUP;
        //deserialize leader cards from json file
        leaderCards = new Stack<>();
        loadLeaderCards();
        //shuffle leader cards
        Collections.shuffle(leaderCards);
        //crates market and card grid
        market = new Market();
        market.addObserverList(this.getMessageObservers());
        cardGrid = new CardGrid();
        cardGrid.AddMatchToNotify(this);
        cardGrid.addObserverList(this.getMessageObservers());
        players = new ArrayList<>();
    }

    public Match(int matchID, int numOfPlayers, Stack<LeaderCard> leaderCards, Market market, CardGrid cardGrid) throws InvalidParameterException{
        this(matchID, numOfPlayers);
        this.leaderCards = leaderCards;
        this.market = market;
        this.cardGrid = cardGrid;
    }

    /**
     * this method loads all the leader cards from the resources of the model
     */
    private void loadLeaderCards(){

        Gson gson = new Gson();

        String pathLeaderCardDepot = "src/main/resources/server/leaderCardDepot.json";
        String pathLeaderCardDiscount = "src/main/resources/server/leaderCardDiscount.json";
        String pathLeaderCardMarble = "src/main/resources/server/leaderCardMarble.json";
        String pathLeaderCardProduction = "src/main/resources/server/leaderCardProduction.json";

        Reader reader = null;

        //Deserializing leaderCardDepot
        try {
            reader = new FileReader(pathLeaderCardDepot);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Type myDataType = new TypeToken<Stack<LeaderDepot>>(){}.getType();
        leaderCardDepot = gson.fromJson(reader, myDataType);

        //Deserializing leaderCardDiscount
        try {
            reader = new FileReader(pathLeaderCardDiscount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        myDataType = new TypeToken<Stack<LeaderDiscount>>(){}.getType();
        leaderCardDiscount = gson.fromJson(reader, myDataType);

        //Deserializing leaderCardMarble
        try {
            reader = new FileReader(pathLeaderCardMarble);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        myDataType = new TypeToken<Stack<LeaderMarble>>(){}.getType();
        leaderCardMarble = gson.fromJson(reader, myDataType);

        //Deserializing leaderCardProduction
        try {
            reader = new FileReader(pathLeaderCardProduction);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        myDataType = new TypeToken<Stack<LeaderProduction>>(){}.getType();
        leaderCardProduction = gson.fromJson(reader, myDataType);

        //Creating one list with all leader cards
        // Push contents from all leader stacks in leaderCards stack
        while (leaderCardDepot.size() != 0) {
            leaderCards.push(leaderCardDepot.pop());
        }

        while (leaderCardDiscount.size() != 0) {
            leaderCards.push(leaderCardDiscount.pop());
        }

        while (leaderCardMarble.size() != 0) {
            leaderCards.push(leaderCardMarble.pop());
        }

        while (leaderCardProduction.size() != 0) {
            leaderCards.push(leaderCardProduction.pop());
        }

    }

    /**
     * This method is called when a player wants to join the match
     * @param nickName represents the nickname of the player that's joining the match
     * @throws InvalidNickName when the nickname requested already exist
     */
    public void addPlayer(String nickName, View view) throws InvalidNickName {
        //checks if the nickname is not taken in this match
        if(players.stream().noneMatch(x -> x.getNickname().equals(nickName))) {
            this.addObserver(view);
            //draw 4 cards from the leader cards stack
            ArrayList<LeaderCard> drawnLeaderCards = new ArrayList<>();
            for(int draws = 0; draws < 4; draws++)
                drawnLeaderCards.add(leaderCards.pop());
            //creates the player and its personal board
            players.add(new Player(nickName, new PersonalBoard(drawnLeaderCards, this),view));
            //updates the number of players ready
            addPlayerReady();
        }
        else
            throw new InvalidNickName();
    }

    /**
     * This method is used to count the players that played their turn in phases in which players dont have to follow an order
     */
    public synchronized void addPlayerReady(){
        numOfPlayersReady++;
        if(numOfPlayers == numOfPlayersReady){
            switch (matchPhase){
                case SETUP:
                    numOfPlayersReady = 0;
                    matchPhase = MatchPhase.LEADERCHOICE;
                    players.forEach(x->x.getPersonalBoard().addObserverList(this.getMessageObservers()));
                    startMatchNotify();
                    return;
                case LEADERCHOICE:
                    numOfPlayersReady = 0;
                    Collections.shuffle(players);
                    currentPlayer = players.get(0);
                    //Assigning resources and faith points according to order
                    for(int playerNumber = 1; playerNumber<=players.size(); playerNumber++){
                        switch (playerNumber){
                            case 1:
                                players.get(0).getPersonalBoard().setNumOfResourcesToChoose(0);
                                break;
                            case 2:
                                players.get(1).getPersonalBoard().setNumOfResourcesToChoose(1);
                                break;
                            case 3:
                                players.get(2).getPersonalBoard().setNumOfResourcesToChoose(1);
                                try {
                                    players.get(2).getPersonalBoard().moveFaithMarker(1);
                                } catch (InvalidParameterException ignored) {
                                }
                                break;
                            case 4:
                                players.get(3).getPersonalBoard().setNumOfResourcesToChoose(2);
                                try {
                                    players.get(2).getPersonalBoard().moveFaithMarker(1);
                                } catch (InvalidParameterException ignored) {
                                }
                                break;
                        }
                    }
                    matchPhase = MatchPhase.RESOURCECHOICE;
            }
        }
    }
    /**
     * This method gives access to the market
     * @return the market of the match
     */
    public Market getMarket() {
        return market;
    }

    /**
     * This method gives access to the card grid
     * @return the card grind of the match
     */
    public CardGrid getCardGrid() {
        return cardGrid;
    }

    /**
     * This method is use to end every turn, it changes the current player and ends the game if the last round ends
     */
    public void nextPlayer(){
        //changes from resource choice phase to standard round phase
        if(matchPhase == MatchPhase.RESOURCECHOICE && ((players.indexOf(currentPlayer) + 1) == numOfPlayers))
            matchPhase = MatchPhase.STANDARDROUND;
        //at the end of the last round ends the game
        if(matchPhase == MatchPhase.LASTROUND && ((players.indexOf(currentPlayer) + 1) == numOfPlayers)){
            endGame();
        }
        //changes the current player to the next player
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % numOfPlayers);
        notifyObservers(new PlayerTurnUpdate(currentPlayer.getNickname()));//updates every player about the new current player
    }

    /**
     * This method is called to know the player that's playing the turn
     * @return the Player currently playing
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer(String nickname) throws InvalidNickName{
        for(Player player : players){
            if(player.getNickname().equals(nickname))
                return player;
        }
        throw new InvalidNickName();
    }

    /**
     * This method activates the vatican report on all players on a specific tile
     * @param tileNumber is the tile to activate
     */
    public void vaticanReport(int tileNumber){
        players.forEach(x -> x.getPersonalBoard().activateVaticanReport(tileNumber));
    }


    /**
     * This method ends the game calculating the victory points from each player and ranking the players
     */
    public void endGame() {
        players.forEach(x -> x.getPersonalBoard().sumVictoryPoints());
        rank = new ArrayList<>(players);
        Collections.sort(rank, new CustomPlayerComparator());
        matchPhase = MatchPhase.GAMEOVER;
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

    public MatchPhase getMatchPhase() {
        return matchPhase;
    }

    public ArrayList<Player> getRank() {
        return rank;
    }

    public Player getPlayerByNickname(String nickname){
        return players.stream().filter(player -> player.getNickname().equals(nickname)).findFirst().orElse(null);
    }

    /**
    * This method is called by the faithTracks and by the developmentCardSpaces when they reaches the condition to end the game
    */
    @Override
    public void update() {
        if(matchPhase == MatchPhase.STANDARDROUND)
            matchPhase = MatchPhase.LASTROUND;
    }

    /**
     * this method updates the view with the market, the card grid and the initial leader card for each player
     */
    private void startMatchNotify(){
        market.doNotify();
        cardGrid.doNotify();
        players.forEach(x-> x.getPersonalBoard().doNotifyLeaders());
    }
}
