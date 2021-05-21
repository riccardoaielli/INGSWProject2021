package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.*;

public class Player {
    String nickname;
    ArrayList <Integer> leaderCards;
    FaithTrack faithTrack;
    DevelopmentCardSpace developmentCardSpace;
    WareHouseDepots wareHouseDepots;
    Strongbox strongbox;
    Map<Marble, Integer> temporaryMarbles;

    public ArrayList<Integer> getLeaderCards() {
        return leaderCards;
    }

    public Player(String nickname) {
        this.nickname = nickname;
        leaderCards = new ArrayList<>();
        temporaryMarbles = new HashMap<>();
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

    public void printPersonalBoards(){
        System.out.println("Personal board stampata");
        printTermporaryMarbles();
        faithTrack.printFaithTrack();
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

    public void printTermporaryMarbles() {
        GetColorString getColorString = new GetColorString();
        String out = "[";
        for(Marble marble: temporaryMarbles.keySet()){
            out = out + getColorString.getColorMarble(marble) + "●: " + temporaryMarbles.get(marble) + " " + cliColor.RESET;
        }
        System.out.println(out.concat("]"));
    }

    public void setTemporaryMarbles(Map<Marble, Integer> temporaryMarbles) {
        this.temporaryMarbles = new HashMap<>(temporaryMarbles);
    }


}
