package it.polimi.ingsw.client.CLI.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.*;

public class PlayerCLI {
    private final int CARD_BACK = 65;
    private String nickname;
    private GetColorString color;
    private ArrayList <Integer> leaderCards;
    private ArrayList <Boolean> leaderCardsActive;
    private ArrayList <ArrayList <Integer> > developmentCardSpace;
    private FaithTrackCLI faithTrackCLI;
    private WareHouseDepotsCLI wareHouseDepots;
    private StrongboxCLI strongbox;
    private Map<Marble, Integer> temporaryMarbles;
    private Map<Resource, Integer> temporaryMapResource;

    public ArrayList<Integer> getLeaderCards() {
        return leaderCards;
    }

    public PlayerCLI(String nickname) {
        this.nickname = nickname;
        color = new GetColorString();
        leaderCards = new ArrayList<>();
        while (leaderCards.size() < 2)
            leaderCards.add(CARD_BACK);
        leaderCardsActive = new ArrayList<>();
        while (leaderCardsActive.size() < 2)
            leaderCardsActive.add(false);
        temporaryMarbles = new HashMap<>();
        temporaryMapResource = new HashMap<>();
        faithTrackCLI = new FaithTrackCLI();
        wareHouseDepots = new WareHouseDepotsCLI();
        strongbox = new StrongboxCLI();
        developmentCardSpace = new ArrayList<>();
    }

    public String getNickname() {
        return nickname;
    }

    public void setRedCrossPosition(int redcrossPosition){
        faithTrackCLI.setRedcrossPosition(redcrossPosition);
    }
    public void setPopeFavourTiles(ArrayList<Integer> popeFavourTiles){ faithTrackCLI.setPopeFavourTiles(popeFavourTiles); }
    public void setWareHouseDepots(List<Map<Resource, Integer>> depots){
        wareHouseDepots.setDepots(depots);
    }
    public void setStrongbox(Map<Resource, Integer> strongbox){
        this.strongbox.setStrongbox(strongbox);
    }
    public void setLeaderCards(ArrayList<Integer> leaderCards) {
        this.leaderCards = new ArrayList<>(leaderCards);
    }

    public void printPersonalBoards(String localPlayer){
        if(localPlayer.equals(nickname))
            System.out.println("Your Personal Board: ");
        else
            System.out.println("Personal board di " + nickname + ":");
        printTermporaryMarbles();
        faithTrackCLI.printFaithTrack();
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
        String out = "[ ";
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

    public void removeCard(int leaderPosition) {
        leaderCards.remove(leaderCards.get(leaderPosition-1));
    }

    public void activateCard(int numLeadercard, int leaderCardID) {
        leaderCards.set(numLeadercard - 1,leaderCardID);
        leaderCardsActive.set(numLeadercard - 1,true);
    }

    public boolean isLeaderActive(int leader) {
        return leaderCardsActive.get(leaderCards.indexOf(leader));
    }

    public ArrayList<ArrayList<Integer>> getDevelopmentCardSpace() {
        return developmentCardSpace;
    }

    public void setDevelopmentCardSpace(ArrayList<ArrayList<Integer>> developmentCardSpace) {
        this.developmentCardSpace = developmentCardSpace;
    }

    public Map<Resource, Integer> getTemporaryMapResource() {
        return temporaryMapResource;
    }

    public void printTermporaryResource() {
        if(!temporaryMapResource.isEmpty()) {
            System.out.print(nickname + " obtained the following resources:[ ");
            temporaryMapResource.keySet().forEach(x -> System.out.print(color.getColorResource(x) + "●" + cliColor.RESET + ": " + temporaryMapResource.get(x) + " "));
            System.out.println("]");
        }
    }
}
