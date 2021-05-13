package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.ErrorMessage;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.utils.observe.MessageObservable;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.Observable;
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

    public synchronized void handleNicknameReplyMessage(String nickname, View virtualView){
        try {
            if(match.getMatchPhase() == MatchPhase.SETUP) {
                match.addPlayer(nickname);
            }
            else{
                //genera un messaggio di errore da madare a un metodo sulla virtualView
            }
        } catch (InvalidNickName invalidNickName) {
            //genera un messaggio di errore da madare a un metodo sulla virtualView
        }
    }

    public synchronized void handleCreateMatchReplyMessage(int numOfPlayers,String nickname, View virtualView){
        try {
            if(match == null){
                match = new Match(1,numOfPlayers);
                //creates a player
                handleNicknameReplyMessage(nickname,virtualView);
            }
            else{
                //genera messaggio di match gia esitente da madare a un metodo sulla virtualView
            }
        }catch (InvalidParameterException exception) {
            //genera messaggio di errore da mandare a un metodo sulla virtualView
        }
    }

    public synchronized void handleTakeFromMarketMessage(int rowOrColumn, int value,String nickname, View virtualView){
        try{
            if((match.getMatchPhase() == MatchPhase.STANDARDROUND || match.getMatchPhase() == MatchPhase.LASTROUND) && match.getCurrentPlayer().getNickname().equals(nickname)){
                match.getCurrentPlayer().getPersonalBoard().takeFromMarket(rowOrColumn,value);
            }
            else{
                //genera un messaggio di fase di gioco non adatta
            }
        } catch (InvalidParameterException exception) {
            //genera messaggio di errore da mandare a un metodo sulla virtualView
        }
    }

    public synchronized void handleTransformWhiteMarblesMessage(int leaderCard,int numOfTransformations, String nickname, View virtualView){
        try{
            if((match.getMatchPhase() == MatchPhase.STANDARDROUND || match.getMatchPhase() == MatchPhase.LASTROUND) && match.getCurrentPlayer().getNickname().equals(nickname) /*&& todo: controllare lo stato della personal board*/){
                match.getCurrentPlayer().getPersonalBoard().transformWhiteMarble(leaderCard,numOfTransformations);
            }
            else{
                //genera un messaggio di fase di gioco non adatta
            }
        } catch (InvalidLeaderAction invalidLeaderAction) {
            //genera messaggio di carta leader non valida
        } catch (InvalidParameterException exception) {
            //genera messaggio di errore da mandare a un metodo sulla virtualView
        } catch (NotEnoughWhiteMarblesException e) {
            //genera messaggio di biglie bianche insufficienti
        }
    }

    public synchronized void handleTransformMarblesMessage(int depotLevel, HashMap<Resource,Integer> singleResourceMap, String nickname){
        try{
            if((match.getMatchPhase() == MatchPhase.STANDARDROUND || match.getMatchPhase() == MatchPhase.LASTROUND) && match.getCurrentPlayer().getNickname().equals(nickname) /*&& todo: controllare lo stato della personal board*/){
                match.getCurrentPlayer().getPersonalBoard().transformMarbles();
                match.getCurrentPlayer().getPersonalBoard().addToWarehouseDepots(depotLevel,singleResourceMap);
            }
            else{
                //genera un messaggio di fase di gioco non adatta
            }
        } catch (InvalidAdditionException e) {
            //genera messaggio di operazione di deposito non valida
        }
    }

    public synchronized void handleDiscardInitialLeaderMessage(View virtualView, String nickname, int indexLeaderCard1, int indexLeaderCard2){
        if (match.getMatchPhase() != MatchPhase.LEADERCHOICE){
            virtualView.update(new ErrorMessage(nickname, "Invalid command"));
            return;
        }

        PersonalBoard personalBoard = match.getPlayerByNickname(nickname).getPersonalBoard();

        if(personalBoard.getPersonalBoardPhase() != PersonalBoardPhase.LEADER_CHOICE){
            virtualView.update(new ErrorMessage(nickname, "Invalid command"));
            return;
        }

        try {
            personalBoard.discardInitialLeader(indexLeaderCard1, indexLeaderCard2);
        } catch (InvalidParameterException e) {
            virtualView.update(new ErrorMessage(nickname, "Invalid command"));
        }
    }

    public synchronized void handleChooseInitialResourcesMessage(View virtualView, String nickname, Map<Resource, Integer> resourceIntegerMap){
        if (match.getMatchPhase() != MatchPhase.RESOURCECHOICE){
            virtualView.update(new ErrorMessage(nickname, "Invalid command"));
            return;
        }
        PersonalBoard personalBoard = match.getPlayerByNickname(nickname).getPersonalBoard();

        if(personalBoard.getPersonalBoardPhase() != PersonalBoardPhase.RESOURCE_CHOICE){
            virtualView.update(new ErrorMessage(nickname, "Invalid command"));
            return;
        }

        try {
            personalBoard.addInitialResources(resourceIntegerMap);
        } catch (InvalidParameterException e) {
            virtualView.update(new ErrorMessage(nickname, "Invalid command"));
        }
    }
}
