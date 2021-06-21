package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.messages.messagesToClient.PlayerTurnUpdate;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.messagesToClient.PlayersOrderUpdate;
import it.polimi.ingsw.common.messages.messagesToClient.RankUpdate;
import it.polimi.ingsw.common.utils.LeaderCardParser;
import it.polimi.ingsw.common.utils.observe.MessageObservable;
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
    private Boolean demo;

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
        LeaderCardParser leaderCardParser= new LeaderCardParser();
        leaderCards = leaderCardParser.loadLeaderCards();
        //shuffle leader cards
        Collections.shuffle(leaderCards);
        //crates market and card grid
        market = new Market();
        market.addObserverList(this.getMessageObservers());
        cardGrid = new CardGrid();
        cardGrid.AddMatchToNotify(this);
        cardGrid.addObserverList(this.getMessageObservers());
        players = new ArrayList<>();
        demo = false;
    }

    //todo: costruttore non utilizzato da eliminare dopo aver completato i tests
    public Match(int matchID, int numOfPlayers, Stack<LeaderCard> leaderCards, Market market, CardGrid cardGrid) throws InvalidParameterException{
        this(matchID, numOfPlayers);
        this.leaderCards = leaderCards;
        this.market = market;
        this.cardGrid = cardGrid;
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
                    //players.forEach(x->x.getPersonalBoard().addObserverList(this.getMessageObservers()));
                    startMatchNotify();
                    return;
                case LEADERCHOICE:
                    numOfPlayersReady = 0;
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
        rank.sort(new CustomPlayerComparator());

        ArrayList<RankPosition> finalRank = new ArrayList<>();
        rank.forEach(x-> finalRank.add(new RankPosition(x.getNickname(),x.getPersonalBoard().getVictoryPoints())));

        matchPhase = MatchPhase.GAMEOVER;
        notifyObservers(new RankUpdate(finalRank));
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

    /**
     * getter for the current phase of the match
     * @return the phase of the match
     */
    public MatchPhase getMatchPhase() {
        return matchPhase;
    }

    //todo: metodo da sostituire con getnickname(getnickname gestisce l'eccezione di nickname non validi)
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

    /**
     * Setter for the demo mode
     */
    public void setDemo() {
        demo = true;
        System.out.println("Demo game: true");
    }
}
