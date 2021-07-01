package it.polimi.ingsw.client.CLI.LocalModel;

import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.*;

/**
 * Class that contains local information of the player and the relatives methods to print information on a CLI
 */
public class PlayerCLI {
    private final int CARD_BACK = 65;
    private final int EMPTY_CARD = 66;
    private final String nickname;
    private final GetColorString color;
    private ArrayList <Integer> leaderCards;
    private ArrayList <Boolean> leaderCardsActive;
    private ArrayList <ArrayList <Integer> > developmentCardSpace;
    private FaithTrackCLI faithTrackCLI;
    private WareHouseDepotsCLI wareHouseDepots;
    private StrongboxCLI strongbox;
    private Map<Marble, Integer> temporaryMarbles;
    private Map<Resource, Integer> temporaryMapResource;
    private ArrayList<String> personalBoardStrings;
    private ArrayList<String> developmentCardSpaceStrings;
    private ArrayList<String> leaderCardsStrings;

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

    /**
     * Getter used to access easily to the development card space
     * @return the local development card space
     */
    public ArrayList<ArrayList<Integer>> getDevelopmentCardSpace() {
        return developmentCardSpace;
    }

    /**
     * Getter used to access easily to the temporary map resource
     * @return the local temporary map resource
     */
    public Map<Resource, Integer> getTemporaryMapResource() {
        return temporaryMapResource;
    }

    /**
     * Getter used to access easily to the leader cards
     * @return the local development leader cards
     */
    public ArrayList<Integer> getLeaderCards() {
        return leaderCards;
    }

    /**
     * Getter used to access easily to the nickname of the player
     * @return the local development nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter used to update the position of the red cross in the faith track
     * @param redcrossPosition is the updated position
     */
    public void setRedCrossPosition(int redcrossPosition){ faithTrackCLI.setRedcrossPosition(redcrossPosition); }

    /**
     * Setter used to update the favour tiles of the faith track
     * @param popeFavourTiles are the favour tiles updated
     */
    public void setPopeFavourTiles(ArrayList<Integer> popeFavourTiles){ faithTrackCLI.setPopeFavourTiles(popeFavourTiles); }

    /**
     * Setter used to update the local warehouse
     * @param depots is the updated version of the warehouse
     */
    public void setWareHouseDepots(List<Map<Resource, Integer>> depots){ wareHouseDepots.setDepots(depots);    }

    /**
     * Setter used to update the local strongbox
     * @param strongbox is the updated version of the strongbox
     */
    public void setStrongbox(Map<Resource, Integer> strongbox){
        this.strongbox.setStrongbox(strongbox);
    }

    /**
     * Setter used to update the leader cards of the player
     * @param leaderCards are the updated leader cards
     */
    public void setLeaderCards(ArrayList<Integer> leaderCards) {
        this.leaderCards = new ArrayList<>(leaderCards);
    }

    /**
     * Setter used to update the temporary marbles of the player
     * @param temporaryMarbles is the updated map of temporary marbles
     */
    public void setTemporaryMarbles(Map<Marble, Integer> temporaryMarbles) {
        this.temporaryMarbles = new HashMap<>(temporaryMarbles);
    }

    /**
     * Setter used to update the temporary map resources of the player
     * @param temporaryMapResource is the updated map of resources
     */
    public void setTemporaryMapResource(Map<Resource, Integer> temporaryMapResource) {
        this.temporaryMapResource = temporaryMapResource;
    }

    /**
     * Setter used to update the development card space of the player
     * @param developmentCardSpace is the updated development card space
     */
    public void setDevelopmentCardSpace(ArrayList<ArrayList<Integer>> developmentCardSpace) {
        this.developmentCardSpace = developmentCardSpace;
    }

    /**
     * Method to discard leaders from the player's leader cards
     * @param indexLeaderCard1 the index of the first leader card to discard
     * @param indexLeaderCard2 the index of the second leader card to discard
     */
    public void discardInitialLeaders(int indexLeaderCard1, int indexLeaderCard2) {
        List<Integer> indexesLeaderCard = new ArrayList<>();
        indexesLeaderCard.add(indexLeaderCard1);
        indexesLeaderCard.add(indexLeaderCard2);
        indexesLeaderCard.sort(Collections.reverseOrder());
        for (int indexLeaderCard: indexesLeaderCard){
            leaderCards.remove(indexLeaderCard-1);
        }
    }

    /**
     * Method to remove one card from the player's leader cards
     * @param leaderPosition the index of the card to discard
     */
    public void removeCard(int leaderPosition) {
        leaderCards.remove(leaderCards.get(leaderPosition-1));
    }

    /**
     * Method to activate a leader card
     * @param numLeadercard the index of the leader card to activate
     * @param leaderCardID the ID of the card to activate
     */
    public void activateCard(int numLeadercard, int leaderCardID) {
        leaderCards.set(numLeadercard - 1,leaderCardID);
        leaderCardsActive.set(numLeadercard - 1,true);
    }

    /**
     * Method to check if a leader card is active
     * @param leader the index of the leader card to check
     * @return true if the card is active
     */
    public boolean isLeaderActive(int leader) {
        return leaderCardsActive.get(leaderCards.indexOf(leader));
    }

    /**
     * Method to print the personal board of the player
     * @param localPlayer the nickname of the local user
     */
    public void printPersonalBoards(String localPlayer){
        personalBoardStrings = new ArrayList<>();
        personalBoardStrings.add("╔═════════════════════════════════════════════════════════════════╗");
        int numOfSpaces = 65;
        String out = "";
        if(localPlayer.equals(nickname)) {
            out = "Your Personal Board: ";
            numOfSpaces = numOfSpaces - out.length();
        }
        else {
            out = ("Personal board di " + nickname + ":");
            numOfSpaces = numOfSpaces - out.length();
        }
        for(int space = 0; space<numOfSpaces; space++){
            out = out + " ";
        }
        personalBoardStrings.add("║" + out + "║");

        //printTermporaryMarbles();

        faithTrackCLI.printFaithTrack();
        personalBoardStrings.add("║" + faithTrackCLI.getFaithTrackByRow(0) + "║");
        personalBoardStrings.add("║" + faithTrackCLI.getFaithTrackByRow(1) + "║");

        wareHouseDepots.printWhareHouseDepots();
        strongbox.printStrongbox();

        for(int row = 0; row < 6; row++){
            if(row == 0 || row == 1){
                out = " " + wareHouseDepots.getByRow(row) + "  " + strongbox.getByRow(row) + "  " + developmentCardSpaceStrings.get(row) + "   ";
            }
            if(row == 2){
                out = " " + wareHouseDepots.getByRow(row) + "                " + developmentCardSpaceStrings.get(row) + "   ";
            }
            if(row > 2){
                out = "                       " + developmentCardSpaceStrings.get(row) + "   ";
            }
            personalBoardStrings.add("║" + out + "║");
        }

        for(String leaderRow: leaderCardsStrings){
            personalBoardStrings.add("║  " + leaderRow + "                                     " + "║" );
        }

        personalBoardStrings.add("╚═════════════════════════════════════════════════════════════════╝");

    }

    public ArrayList<String> getPersonalBoardStrings() {
        return personalBoardStrings;
    }

    /**
     * Method to print the temporary map of resources
     */
    public void printTermporaryResource() {
        if(!temporaryMapResource.isEmpty()) {
            System.out.print(nickname + " obtained the following resources:[ ");
            temporaryMapResource.keySet().forEach(x -> System.out.print(color.getColorResource(x) + "●" + CliColor.RESET + ": " + temporaryMapResource.get(x) + " "));
            System.out.println("]");
        }
    }

    /**
     * Method to print the temporary map of marbles
     */
    public void printTermporaryMarbles() {
        if(temporaryMarbles.isEmpty())
            return;
        GetColorString getColorString = new GetColorString();
        String out = "[ ";
        for(Marble marble: temporaryMarbles.keySet()){
            out = out + getColorString.getColorMarble(marble) + "●: " + temporaryMarbles.get(marble) + " " + CliColor.RESET;
        }
        System.out.println(out.concat("]"));
    }

    public void printDevelopmentCardSpace(Map<Integer, ArrayList<String>> cliCardString) {
        developmentCardSpaceStrings = new ArrayList<>();
        String rowString = "";
        for(int cardRow = 0; cardRow < 6; cardRow++) {
            if(developmentCardSpace.size() != 0) {
                for (ArrayList<Integer> row : developmentCardSpace) {
                    if (row.size() != 0) {
                        for (int card : row) {
                            if (row.indexOf(card) == (row.size() - 1))
                                rowString = rowString.concat(cliCardString.get(card).get(cardRow));
                        }
                    } else
                        rowString = rowString.concat(cliCardString.get(EMPTY_CARD).get(cardRow));
                }
            }
            else
                rowString = cliCardString.get(EMPTY_CARD).get(cardRow) + cliCardString.get(EMPTY_CARD).get(cardRow) + cliCardString.get(EMPTY_CARD).get(cardRow);

            developmentCardSpaceStrings.add(rowString);
            //System.out.println(rowString);
            rowString = "";
        }
    }

    public void printLeaderCards(Map<Integer, ArrayList<String>> cliCardString) {
        leaderCardsStrings = new ArrayList<>();
        String rowString = "";
        for(int cardRow = 0; cardRow < 5; cardRow++){
            for(Integer card : leaderCards) {
                if(cardRow == 4 && isLeaderActive(card)){
                    rowString = rowString + CliColor.COLOR_YELLOW + cliCardString.get(card).get(cardRow) + CliColor.RESET;
                }
                else
                    rowString = rowString.concat(cliCardString.get(card).get(cardRow));
            }
            leaderCardsStrings.add(rowString);
            //System.out.println(rowString);
            rowString = "";
        }
    }
}
