package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.ErrorMessage;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.server.model.Match;
import it.polimi.ingsw.server.model.PersonalBoard;
import it.polimi.ingsw.server.model.enumerations.MatchPhase;
import it.polimi.ingsw.server.model.enumerations.PersonalBoardPhase;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;

import java.util.Map;

public class Controller {
    private Match match;

    public void handleNicknameReplyMessage(String nickname){

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
