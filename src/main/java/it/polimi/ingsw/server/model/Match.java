package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.messages.messagesToClient.PlayerTurnUpdate;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.messagesToClient.PlayersOrderUpdate;
import it.polimi.ingsw.common.messages.messagesToClient.RankUpdate;
import it.polimi.ingsw.common.utils.LeaderCardParser;
import it.polimi.ingsw.common.utils.observe.MessageObservable;
import it.polimi.ingsw.server.model.comparators.CustomPlayerComparator;
import it.polimi.ingsw.server.model.enumerations.MatchPhase;
import it.polimi.ingsw.server.model.enumerations.PersonalBoardPhase;
import it.polimi.ingsw.server.model.exceptions.InvalidNickName;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * This class represents the a match of the game, it coordinates the players and their turns.
 * The controller interacts with the model by the methods of this class
 */
public class Match extends MessageObservable implements EndGameConditionsObserver {
    private final int matchID;
    private final int numOfPlayers;
    private int numOfPlayersReady;
    private int numOfTurnsPlayed;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private Stack<LeaderCard> leaderCards;
    private MatchPhase matchPhase;
    private Market market;
    private CardGrid cardGrid;
    private ArrayList<Player> rank;
    private ArrayList<RankPosition> finalRank;
    private Boolean demo;

    /**
     * This constructor creates a match deserializing all the leader cards and creating the market and the cardgrind
     * @param matchID an int that identifies the match
     * @param numOfPlayer the number of players that will join the match
     */
    public Match(int matchID, int numOfPlayer, boolean demo) throws InvalidParameterException {
        if (numOfPlayer < 0 || numOfPlayer > 4)
            throw new InvalidParameterException("The match can be played by one to four players");
        this.matchID = matchID;
        this.numOfPlayers = numOfPlayer;
        numOfTurnsPlayed = 0;
        numOfPlayersReady = 0;
        matchPhase = MatchPhase.SETUP;
        //deserialize leader cards from json file
        LeaderCardParser leaderCardParser= new LeaderCardParser();
        leaderCards = leaderCardParser.loadLeaderCards();
        //shuffle leader cards
        if(!demo)
         Collections.shuffle(leaderCards);
        //crates market and card grid
        market = new Market(demo);
        market.addObserverList(this.getMessageObservers());
        cardGrid = new CardGrid(demo);
        cardGrid.AddMatchToNotify(this);
        cardGrid.addObserverList(this.getMessageObservers());
        players = new ArrayList<>();
        this.demo = demo;
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
            if(demo)
                drawnLeaderCards = drawLeaderCardsDemo(players.size());
            else {
                for (int draws = 0; draws < 4; draws++)
                    drawnLeaderCards.add(leaderCards.pop());
            }
            //creates the player and its personal board
            players.add(new Player(nickName, new PersonalBoard(drawnLeaderCards, this),view));
            //updates the number of players ready
            addPlayerReady();
        }
        else
            throw new InvalidNickName("The nickname " + nickName + " is taken");
    }

    private ArrayList<LeaderCard> drawLeaderCardsDemo(int playerNumber) {
        ArrayList<LeaderCard> drawnLeaderCards = new ArrayList<>();
        for (int numCard = 0; numCard < 16; numCard+=4)
            drawnLeaderCards.add(leaderCards.get(numCard + playerNumber));
        return drawnLeaderCards;
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
                    //players.forEach(x->x.getPersonalBoard().addObserverList(this.getMessageObservers()));
                    startMatchNotify();
                    return;
                case LEADERCHOICE:
                    numOfPlayersReady = 0;
                    if(!demo)
                        Collections.shuffle(players);

                    //Sending order of players
                    List<String> playerNicknames = new ArrayList<>();
                    for(Player player : players){
                       playerNicknames.add(player.getNickname());
                    }
                    notifyObservers(new PlayersOrderUpdate(null, playerNicknames));
                    currentPlayer = players.get(0);
                    if(demo)
                        players.forEach(player -> player.getPersonalBoard().setDemo());

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
                                players.get(2).getPersonalBoard().moveFaithMarker(1);
                                break;
                            case 4:
                                players.get(3).getPersonalBoard().setNumOfResourcesToChoose(2);
                                players.get(3).getPersonalBoard().moveFaithMarker(1);
                                break;
                        }
                    }
                    matchPhase = MatchPhase.RESOURCECHOICE;
                    nextPlayer();
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
        if(matchPhase == MatchPhase.RESOURCECHOICE && ((players.indexOf(currentPlayer) + 1) == numOfPlayers)) {
            matchPhase = MatchPhase.STANDARDROUND;
            for(Player player: players)
                player.getPersonalBoard().setPersonalBoardPhase(PersonalBoardPhase.MAIN_TURN_ACTION_AVAILABLE);
        }
        else
            numOfTurnsPlayed++;
        //at the end of the last round ends the game
        if(matchPhase == MatchPhase.LASTROUND && ((players.indexOf(currentPlayer) + 1) == numOfPlayers)){
            endGame();
            return;
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

    /**
     * getter for a player by nickname
     * @param nickname the nickname of the player
     * @return the player with the given nickname
     * @throws InvalidNickName when a player with the given nickname does not exist
     */
    public Player getPlayer(String nickname) throws InvalidNickName{
        for(Player player : players){
            if(player.getNickname().equals(nickname))
                return player;
        }
        throw new InvalidNickName("A player called " + nickname + " does not exist");
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
        rank.sort(new CustomPlayerComparator());
        ArrayList<Player> tempRank = new ArrayList<>();
        for (Player player: rank)
            tempRank.add(rank.get(rank.size() - rank.indexOf(player) - 1));
        rank = new ArrayList<>(tempRank);
        finalRank = new ArrayList<>();
        rank.forEach(x-> finalRank.add(new RankPosition(x.getNickname(),x.getPersonalBoard().getVictoryPoints())));

        matchPhase = MatchPhase.GAMEOVER;
        notifyObservers(new RankUpdate(finalRank));
    }

    /**
     * Getter for testing purpose
     * @return the final rank
     */
    public ArrayList<RankPosition> getFinalRank() {
        return finalRank;
    }

    /**
     * This method moves the faith marker for all the players out of the current player
     * @param positions is the number of steps to make on the faith track for each player
     */
    public void moveFaithMarkerAll(int positions){
        players.stream().filter(x -> x != currentPlayer).forEach(x -> x.getPersonalBoard().moveFaithMarker(positions));
        players.stream().filter(x -> x != currentPlayer).forEach(x -> x.getPersonalBoard().checkVaticanReport());
    }

    /**
     * getter for the current phase of the match
     * @return the phase of the match
     */
    public MatchPhase getMatchPhase() {
        return matchPhase;
    }

    /**
    * This method is called by the faithTracks and by the developmentCardSpaces when they reaches the condition to end the game
     * @param message
     */
    @Override
    public void update(boolean message) {
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
