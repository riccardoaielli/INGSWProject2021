package it.polimi.ingsw.client;

import it.polimi.ingsw.client.CLI.LocalModel.LocalModel;
import it.polimi.ingsw.client.CLI.LocalModel.LocalPhase;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.model.RankPosition;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Interface implemented by GUI and CLI containing methods used to display changes and messages from controller and model
 */
public interface ClientView extends View {
    /**
     * Method to start the view
     */
    void start();

    /**
     * Method to ask the first player to create a match
     */
    void askCreateMatch();

    /**
     * Method to ask a player the nickname to join the match
     */
    void askNickname();

    /**
     * Method to ask the player to choose it's starting resources
     */
    void askResourceChoice();

    /**
     * Method to ask the player the resources to add to its warehouse
     */
    void askAddToWareHouse();

    /**
     * Method to ask the player an action to do
     */
    void askTurnAction();

    /**
     * Method to ask the player a card to buy
     */
    void askBuyDevCard();

    /**
     * Method to ask the player a production to activate
     */
    void askProduction();

    /**
     * Method to ask the player a leader power to use
     */
    void askForLeaderPower();

    /**
     * Method to ask the player the coordinates to buy from the market
     */
    void askTakeFromMarketAction();

    /**
     * Method to ask the player to rearrange it's depots
     */
    void askRearrange();

    /**
     * Getter for the local model of the view
     * @return a local model
     */
    LocalModel getLocalModel();

    /**
     * Method to print an error received from the Model
     * @param errorString
     */
    void showError(String errorString);

    /**
     * Method to update the leader cards received from the Model
     * @param nickname the nickname of the player that received the update
     * @param indexLeaderCard1 the index of the first leader card
     * @param indexLeaderCard2 the index of the second leader card
     */
    void showInitialLeaderCardDiscard(String nickname, int indexLeaderCard1 , int indexLeaderCard2);

    /**
     * Method to update the players order received from the Model
     * @param playersOrder a list of players
     */
    void showUpdatePlayersOrder(List<String> playersOrder);

    /**
     * Method to update the temporary resources received from the Model
     * @param nickname the nickname of the player that received the update
     * @param temporaryMapResource the updated temporary resources
     */
    void showUpdatedTemporaryMapResource(String nickname, Map<Resource, Integer> temporaryMapResource);

    /**
     * Method to update the warehouse received from the Model
     * @param nickname the nickname of the player that received the update
     * @param depots the updated warehouse
     */
    void showUpdatedWarehouse(String nickname, List<Map<Resource, Integer>> depots);

    /**
     * Method to update the strongbox received from the Model
     * @param nickname the nickname of the player that received the update
     * @param strongbox the updated strongbox
     */
    void showUpdatedStrongbox(String nickname, Map<Resource, Integer> strongbox);

    /**
     * Method to update the development card space received from the Model
     * @param nickname the nickname of the player that received the update
     * @param cardsState the updated development card space
     */
    void showUpdatedDevCardSpace(String nickname, ArrayList<ArrayList<Integer>> cardsState);

    /**
     * Method to update the temporary marbles received from the Model
     * @param nickname the nickname of the player that received the update
     * @param temporaryMarbles the updated temporary marbles
     */
    void showUpdateTemporaryMarbles(String nickname,Map<Marble,Integer> temporaryMarbles);

    /**
     * Method to update the position of the red cross received from the Model
     * @param nickname the nickname of the player that received the update
     * @param redcrossPosition the updated position of the red cross
     */
    void showUpdateRedcrossPosition(String nickname,int redcrossPosition);

    /**
     * Method to update the pope favour tiles values received from the Model
     * @param nickname the nickname of the player that received the update
     * @param popeFavourTiles the updated pope favour tiles values
     */
    void showUpdatePopeFavourTiles(String nickname,ArrayList<Integer> popeFavourTiles);

    /**
     * Method to update the player's turn received from the Model
     * @param nickname the nickname of the player that has to play the turn
     */
    void showUpdatePlayerTurn(String nickname);

    /**
     * Method to update the market received from the Model
     * @param marketMatrix the updated market
     * @param marbleOut the updated marble out
     */
    void showUpdateMarket(Marble[][] marketMatrix,Marble marbleOut);

    /**
     * Method to update the adding of a special depot received from the Model
     * @param nickname the nickname of the player that received the update
     * @param depotResourceType the resource type of the new depot
     */
    void showUpdateAddSpecialDepotUpdate(String nickname, Resource depotResourceType);

    /**
     * Method to update the card grid received from the Model
     * @param cardGridMatrixUpdate the updated card grid
     */
    void showUpdateCardGridUpdate(int[][] cardGridMatrixUpdate);

    /**
     * Method to update the discarded leader card from a player received from the model
     * @param nickname the nickname of the player that received the update
     * @param leaderPosition the index of the discarded leader card
     */
    void showUpdateDiscardedLeaderUpdate(String nickname, int leaderPosition);

    /**
     * Method to update the activation of a leader card received from the Model
     * @param nickname the nickname of the player that received the update
     * @param numLeadercard the index of the activated leader card
     * @param leaderCardID the ID of the activated leader card
     */
    void showUpdateLeaderCardActivatedUpdate(String nickname, int numLeadercard, int leaderCardID);

    /**
     * Method to show the rank received from the Model at the end of the game
     * @param nickname the player that received the update
     * @param rank the rank generated at the end of the game
     */
    void showUpdateRank(String nickname,ArrayList<RankPosition> rank);

    /**
     * Method to update the initial leader cards received from the Model
     * @param initialLeaderCardsID a list of four leader cards
     */
    void showUpdateInitialLeaderCard(ArrayList<Integer> initialLeaderCardsID);

    /**
     * Method to update the position of Lorenzo's black cross received from the Model
     * @param blackCrossPosition the updated position of the black cross
     */
    void showUpdateBlackcrossPosition(int blackCrossPosition);

    /**
     * Method to show the card that lorenzo discarded from the card grid
     * @param nickname the nickname of the player that received the update
     * @param row the row in the card grid
     * @param column the column in the card grid
     */
    void showUpdateLorenzoDraw(String nickname,int row,int column);

    /**
     * Method to ask the player two leader cards to discard
     */
    void askForLeaderCards();

    /**
     * Getter for the nickname associated with the view
     * @return a nickname
     */
    String getNickname();

    /**
     * Method to ask a player a leader card to discard
     */
    void askDiscardLeader();

    /**
     * Method to update the turn phase of the view
     * @param phase the update of the phase
     */
    void setPhase(LocalPhase phase);

    /**
     * Method to set if the player did or not the main turn action
     * @param mainTurnActionDone true if the player already did the action
     */
    void setMainTurnActionDone(boolean mainTurnActionDone);

    /**
     * Getter for the phase of the view
     * @return the current phase of the view
     */
    LocalPhase getPhase();

    /**
     * Method to ask a player a leader card to activate
     */
    void askActivateLeader();

    /**
     * Method to close the game
     * @param string the reason why the game is closed
     */
    void closeGame(String string);
}
