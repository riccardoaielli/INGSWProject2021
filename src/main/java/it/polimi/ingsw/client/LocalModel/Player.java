package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.*;

public class Player {
    private String nickname;
    private ArrayList <Integer> leaderCards;
    private FaithTrack faithTrack;
    private DevelopmentCardSpace developmentCardSpace;
    private WareHouseDepots wareHouseDepots;
    private Strongbox strongbox;
    private Map<Marble, Integer> temporaryMarbles;
    private Map<Resource, Integer> temporaryMapResource;

    public ArrayList<Integer> getLeaderCards() {
        return leaderCards;
    }

    public Player(String nickname) {
        this.nickname = nickname;
        leaderCards = new ArrayList<>();
        temporaryMarbles = new HashMap<>();
        temporaryMapResource = new HashMap<>();
        faithTrack = new FaithTrack();
        wareHouseDepots = new WareHouseDepots();
        strongbox = new Strongbox();
    }

    public String getNickname() {
        return nickname;
    }

    public void setRedCrossPosition(int redcrossPosition){
        faithTrack.setRedcrossPosition(redcrossPosition);
    }
    public void setPopeFavourTiles(ArrayList<Integer> popeFavourTiles){
        faithTrack.setPopeFavourTiles(popeFavourTiles);
    }
    public void setDevelopmentCardSpace(ArrayList<ArrayList<Integer>> developmentCardSpace){

    }
    public void setWareHouseDepots(List<Map<Resource, Integer>> depots){
        wareHouseDepots.setDepots(depots);
    }
    public void setStrongbox(Map<Resource, Integer> strongbox){
        this.strongbox.setStrongbox(strongbox);
    }

    public void setLeaderCards(ArrayList<Integer> leaderCards) {
        this.leaderCards = new ArrayList<>(leaderCards);
    }

    public void printPersonalBoards(){
        System.out.println("Personal board di " + nickname + ":");
        printTermporaryMarbles();
        faithTrack.printFaithTrack();
        wareHouseDepots.printWhareHouseDepots();
        strongbox.printStrongbox();
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
        if(temporaryMarbles.isEmpty())
            return;
        GetColorString getColorString = new GetColorString();
        String out = "MARBLES OBTAINED FROM THE MARKET:[ ";
        for(Marble marble: temporaryMarbles.keySet()){
            out = out + getColorString.getColorMarble(marble) + "●: " + temporaryMarbles.get(marble) + " " + cliColor.RESET;
        }
        System.out.println(out.concat("]"));
    }

    public void setTemporaryMarbles(Map<Marble, Integer> temporaryMarbles) {
        this.temporaryMarbles = new HashMap<>(temporaryMarbles);
    }


    public void setTemporaryMapResource(Map<Resource, Integer> temporaryMapResource) {
        this.temporaryMapResource = temporaryMapResource;
    }
}
