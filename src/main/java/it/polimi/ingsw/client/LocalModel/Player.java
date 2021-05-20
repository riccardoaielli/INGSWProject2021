package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Player {
    String nickname;
    ArrayList <Integer> leaderCards;
    FaithTrack faithTrack;
    DevelopmentCardSpace developmentCardSpace;
    WareHouseDepots wareHouseDepots;
    Strongbox strongbox;

    public Player(String nickname) {
        this.nickname = nickname;
        leaderCards = new ArrayList<>();
    }

    public String getNickname() {
        return nickname;
    }

    public void setRedCrossPosition(int redcrossPosition){

    }
    public void setPopeFavourTiles(ArrayList<Integer> popeFavourTiles){

    }
    public void setDevelopmentCardSpace(ArrayList<ArrayList<Integer>> developmentCardSpace){

    }
    public void setWareHouseDepots(List<Map<Resource, Integer>> depots){

    }
    public void setStrongbox(Map<Resource, Integer> strongbox){

    }

    public void setLeaderCards(ArrayList<Integer> leaderCards) {
        this.leaderCards = new ArrayList<>(leaderCards);
    }

    private void printPersonalBoards(){

    }


    public void discardInitialLeaders(int indexLeaderCard1, int indexLeaderCard2) {
        List<Integer> indexesLeaderCard = new ArrayList<>();
        indexesLeaderCard.add(indexLeaderCard1);
        indexesLeaderCard.add(indexLeaderCard2);
        indexesLeaderCard.sort(Collections.reverseOrder());
        for (int indexLeaderCard: indexesLeaderCard){
            leaderCards.remove(indexLeaderCard-1);
        }
    }
}
