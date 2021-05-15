package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.ErrorMessage;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.utils.observe.MessageObservable;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.PersonalBoard;
import it.polimi.ingsw.server.model.enumerations.MatchPhase;
import it.polimi.ingsw.server.model.enumerations.PersonalBoardPhase;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.*;

import java.util.HashMap;
import java.util.Map;

public class Controller extends MessageObservable{
    private Match match;

    public Controller() { 
    }


    /**
     * Handles a nicknameReplyMessage
     * @param nickname is the nickname to add to the match
     * @param view is the view of the client that sends the message
     */
    public synchronized void handleNicknameReplyMessage(String nickname, View view){
        try {
            if(match.getMatchPhase() == MatchPhase.SETUP) {
                match.addPlayer(nickname,view);
            }
            else{
                view.update(new ErrorMessage(nickname, "Invalid command"));
            }
        } catch (InvalidNickName invalidNickName) {
            view.update(new ErrorMessage(nickname, "Invalid nickname"));
        }
    }

    /**
     * Handles a createMatchReplyMessage
     * @param numOfPlayers is the number of players that the new match will require to start
     * @param nickname is the nickname of the first player
     * @param view is the view of the client that sends the message
     */
    public synchronized void handleCreateMatchReplyMessage(int numOfPlayers,String nickname, View view){
        try {
            if(match == null){
                match = new Match(1,numOfPlayers);
                //creates a player
                handleNicknameReplyMessage(nickname,view);
            }
            else{
                view.update(new ErrorMessage(nickname, "Invalid command"));
            }
        }catch (InvalidParameterException exception) {
            view.update(new ErrorMessage(nickname, "Invalid number of players"));
        }
    }

    public synchronized void handleActivateLeader(int numLeaderCard, String nickname, View view){
        try {
            if(match.getCurrentPlayer().getNickname().equals(nickname)
                && match.getCurrentPlayer().getPersonalBoard().getPersonalBoardPhase() == PersonalBoardPhase.MAIN_TURN_ACTION_AVAILABLE
                    || match.getCurrentPlayer().getPersonalBoard().getPersonalBoardPhase() == PersonalBoardPhase.MAIN_TURN_ACTION_DONE){
                match.getCurrentPlayer().getPersonalBoard().activateLeader(numLeaderCard);
            }
        } catch (InvalidParameterException exception) {
            view.update(new ErrorMessage(nickname, "Invalid Leader Card"));
        } catch (RequirementNotMetException e) {
            view.update(new ErrorMessage(nickname, "Invalid Request"));
        }
    }

    /**
     * Handles a TakeFromMarketMessage
     * @param rowOrColumn 0 if row, 1 if column
     * @param value from 0 to 2 if row, from 0 to 3 if column
     * @param nickname is the nickname of the player that sends the message
     * @param view is the view of the client that sends the message
     */
    public synchronized void handleTakeFromMarketMessage(int rowOrColumn, int value,String nickname, View view){
        try{
            if((match.getMatchPhase() == MatchPhase.STANDARDROUND || match.getMatchPhase() == MatchPhase.LASTROUND)
                    && match.getCurrentPlayer().getNickname().equals(nickname)){
                match.getCurrentPlayer().getPersonalBoard().takeFromMarket(rowOrColumn,value);
            }
            else{
                view.update(new ErrorMessage(nickname, "Invalid command"));
            }
        } catch (InvalidParameterException exception) {
            view.update(new ErrorMessage(nickname, "Invalid command"));
        }
    }

    /**
     * Handles a TransformWhiteMarblesMessage
     * @param leaderCard is the number of the card to use to transform marbles
     * @param numOfTransformations is the number of marbles that needs to be transformed
     * @param nickname is the nickname of the player that sends the message
     * @param view is the view of the client that sends the message
     */
    public synchronized void handleTransformWhiteMarblesMessage(int leaderCard,int numOfTransformations, String nickname, View view){
        try{
            if((match.getMatchPhase() == MatchPhase.STANDARDROUND || match.getMatchPhase() == MatchPhase.LASTROUND)
                    && match.getCurrentPlayer().getNickname().equals(nickname)
                    && match.getCurrentPlayer().getPersonalBoard().getPersonalBoardPhase() == PersonalBoardPhase.TAKE_FROM_MARKET){
                match.getCurrentPlayer().getPersonalBoard().transformWhiteMarble(leaderCard,numOfTransformations);
            }
            else{
                view.update(new ErrorMessage(nickname, "Invalid command"));
            }
        } catch (InvalidLeaderAction invalidLeaderAction) {
            view.update(new ErrorMessage(nickname, "Invalid leader card selected"));
        } catch (InvalidParameterException exception) {
            view.update(new ErrorMessage(nickname, "Invalid command"));
        } catch (NotEnoughWhiteMarblesException e) {
            view.update(new ErrorMessage(nickname, "Not enough white marbles to transform"));
        }
    }

    /**
     * Handles a TransformMarblesMessage
     * @param view is the view of the client that sends the message
     * @param nickname is the nickname of the player that sends the message
     */
    public synchronized void handleTransformMarblesMessage(View view, String nickname){
        if((match.getMatchPhase() == MatchPhase.STANDARDROUND || match.getMatchPhase() == MatchPhase.LASTROUND)
                && match.getCurrentPlayer().getNickname().equals(nickname)
                && match.getCurrentPlayer().getPersonalBoard().getPersonalBoardPhase() == PersonalBoardPhase.TAKE_FROM_MARKET){
            match.getCurrentPlayer().getPersonalBoard().transformMarbles();
        }
        else{
            view.update(new ErrorMessage(nickname, "Invalid command"));
        }
    }

    /**
     * Method used to discard the two leader card selected by the player
     * @param view The view of the player whose leader cards have to be discarded
     * @param nickname The nickname of the player
     * @param indexLeaderCard1 The index of one of the two leader card to discard
     * @param indexLeaderCard2 The index of one of the two leader card to discard
     */
    public synchronized void handleDiscardInitialLeaderMessage(View view, String nickname, int indexLeaderCard1, int indexLeaderCard2){
        if (match.getMatchPhase() != MatchPhase.LEADERCHOICE){
            view.update(new ErrorMessage(nickname, "Invalid command"));
            return;
        }

        PersonalBoard personalBoard = match.getPlayerByNickname(nickname).getPersonalBoard();

        if(personalBoard.getPersonalBoardPhase() != PersonalBoardPhase.LEADER_CHOICE){
            view.update(new ErrorMessage(nickname, "Invalid command"));
            return;
        }

        try {
            personalBoard.discardInitialLeader(indexLeaderCard1, indexLeaderCard2);
        } catch (InvalidParameterException e) {
            view.update(new ErrorMessage(nickname, "Invalid command"));
        }
    }

    /**
     * Method used to communicate to the model the resources chosen by player
     * @param view View of the player who has chosen the resources
     * @param nickname Nickname of the player
     * @param resourceIntegerMap Map of the resources and their quantities chosen by player
     */
    public synchronized void handleChooseInitialResourcesMessage(View view, String nickname, Map<Resource, Integer> resourceIntegerMap){
        if (match.getMatchPhase() != MatchPhase.RESOURCECHOICE){
            view.update(new ErrorMessage(nickname, "Invalid command"));
            return;
        }
        PersonalBoard personalBoard = match.getPlayerByNickname(nickname).getPersonalBoard();

        if(personalBoard.getPersonalBoardPhase() != PersonalBoardPhase.RESOURCE_CHOICE){
            view.update(new ErrorMessage(nickname, "Invalid command"));
            return;
        }

        try {
            personalBoard.addInitialResources(resourceIntegerMap);
        } catch (InvalidParameterException e) {
            view.update(new ErrorMessage(nickname, "Invalid command"));
        }
    }

    public synchronized void handleAddToWarehouseMessage(View view, String nickname, int depotLevel, HashMap<Resource,Integer> singleResourceMap){
        PersonalBoard personalBoard = match.getPlayerByNickname(nickname).getPersonalBoard();
        try {
            personalBoard.addToWarehouseDepots(depotLevel, singleResourceMap);
        } catch (InvalidAdditionException e) {
            view.update(new ErrorMessage(nickname, e.getMessage()));
        }
    }
}
