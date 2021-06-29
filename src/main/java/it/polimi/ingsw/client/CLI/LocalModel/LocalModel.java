package it.polimi.ingsw.client.CLI.LocalModel;

import it.polimi.ingsw.common.utils.CardGridParser;
import it.polimi.ingsw.common.utils.LeaderCardParser;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.DevelopmentCard;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.*;
import java.util.List;

/***
 * This class contains a local model with the necessary information to update the CLI or the GUI
 */
public class LocalModel {
    private final int CARD_BACK = 65;
    private final int EMPTY_CARD = 66;
    private final int CARD_WIDTH = 13;
    private final GetColorString getColorString = new GetColorString();
    private final Map<Integer,ArrayList <String>> cliCardString = new HashMap<>();

    private ArrayList <PlayerCLI> players;
    private PlayerCLI localPlayer;
    private MarketCLI market;
    private int blackCrossPosition;
    private final int maxRow = 3, maxColumn = 4;
    private int[][] cardGridMatrix;

    public LocalModel() {
        players = new ArrayList<>();
        market = new MarketCLI();
        cliCardStringCreator();
    }

    /**
     * Getter for Local Player data
     * @return
     */
    public PlayerCLI getLocalPlayer() {
        return localPlayer;
    }

    /**
     * This method updates the local model with the market structure received from a model update or a market update message
     * @param marketMatrix is a matrix that represents the market
     * @param marbleOut is the marble out of the market
     */
    public void setMarket(Marble[][] marketMatrix,Marble marbleOut){
        market.setMarketMatrix(marketMatrix);
        market.setMarbleOut(marbleOut);
    }

    /**
     * This method updates the local model with the cardGrid structure received from a model update or a cardGrid update message
     * @param cardGridMatrix is a matrix that represents the cardGrid, each position refers to a development card ID
     */
    public void setCardGrid(int[][] cardGridMatrix){
        this.cardGridMatrix = cardGridMatrix;
    }

    /**
     * this method updates the first four cards that the player receives at the beginning of the game
     * @param initialLeaderCards a list of cards id
     */
    public void setInitialLeaderCards(ArrayList<Integer> initialLeaderCards){
        localPlayer.setLeaderCards(initialLeaderCards);
    }

    /**
     * This method returns player from a given nickname
     * @param nickname is the nickname of a player
     * @return the player with the given nickname
     */
    public PlayerCLI getPlayer(String nickname){
       for(PlayerCLI player: players){
           if (player.getNickname().equals(nickname))
               return player;
       }
       return null;
    }

    /**
     * Method to print a development card or a leader card
     * @param cardID the ID of the card to print
     */
    public void printCard(int cardID) {
        if(cardID==CARD_BACK)
            System.out.println("CARTA COPERTA");
        else {
            ArrayList<String> arrayList = cliCardString.get(cardID);
            for (int k = 0; k < arrayList.size(); k++)
                System.out.print(arrayList.get(k) + "\n");
        }
    }

    /**
     * Method to print the market
     */
    public void printMarket(){
        market.printMarket();
    }

    /**
     * Method to print the card grid
     */
    public void printCardGrid(){
        String cardRowString = "";
        for(int row = 0; row < maxRow ; row++){
            for(int cardRow = 0; cardRow < 6; cardRow++){
                for(int column = 0; column < maxColumn; column++){
                    if(cardGridMatrix[row][column] != 0)
                        cardRowString = cardRowString.concat(cliCardString.get(cardGridMatrix[row][column]).get(cardRow));
                    else
                        cardRowString = cardRowString.concat(cliCardString.get(EMPTY_CARD).get(cardRow));
                }
                System.out.println(cardRowString);
                cardRowString = "";
            }
        }
    }

    /**
     * Method to print the initial leader cards
     */
    public void printInitialLeaderCards(){
        String rowString = "";
        ArrayList <Integer> leaderCard = localPlayer.getLeaderCards();
        for(int cardRow = 0; cardRow < 5; cardRow++){
            for(Integer card : leaderCard) {
                rowString = rowString.concat(cliCardString.get(card).get(cardRow));
            }
            System.out.println(rowString);
            rowString = "";
        }
    }

    /**
     * Method to print the leader cards of a player
     * @param player the nickname of the player
     */
    public void printLeaderCards(PlayerCLI player) {
        String rowString = "";
        ArrayList <Integer> leaderCard = player.getLeaderCards();
        for(int cardRow = 0; cardRow < 5; cardRow++){
            for(Integer card : leaderCard) {
                if(cardRow == 4 && player.isLeaderActive(card)){
                    rowString = rowString + CliColor.COLOR_YELLOW + cliCardString.get(card).get(cardRow) + CliColor.RESET;
                }
                else
                    rowString = rowString.concat(cliCardString.get(card).get(cardRow));
            }
            System.out.println(rowString);
            rowString = "";
        }
    }

    /**
     * Method to print the development cards of a player
     * @param player the nickname of a player
     */
    private void printDevelopmentCards(PlayerCLI player) {
        String rowString = "";
        ArrayList <ArrayList<Integer>> developmentCardSpace = player.getDevelopmentCardSpace();
        for(int cardRow = 0; cardRow < 6; cardRow++) {
            for (ArrayList<Integer> row : developmentCardSpace) {
                if(row.size() != 0) {
                    for (int card : row) {
                        if (row.indexOf(card) == (row.size() - 1))
                            rowString = rowString.concat(cliCardString.get(card).get(cardRow));
                    }
                }else
                    rowString = rowString.concat(cliCardString.get(EMPTY_CARD).get(cardRow));
            }

            System.out.println(rowString);
            rowString = "";
        }
    }

    /**
     * Setter for the local player's nickname
     * @param localPlayer the nickname typed by the user
     */
    public void setLocalPlayer(String localPlayer) {
        this.localPlayer = new PlayerCLI(localPlayer);
        players.add(this.localPlayer);
    }

    /**
     * Method to print the complete view
     */
    public void printView(LocalPhase phase){
        if(phase != LocalPhase.LEADER_CHOICE && phase != LocalPhase.DEFAULT && phase != LocalPhase.FIRST_PLAYER && phase != LocalPhase.NICKNAME && phase != LocalPhase.RESOURCE_CHOICE) {
            if (market != null)
                printMarket();
            if (cardGridMatrix != null)
                printCardGrid();
            if (players.size() != 0) {
                if (players.size() == 1)
                    printLorenzosFaithTrack();
                for (PlayerCLI player : players) {
                    player.printPersonalBoards(localPlayer.getNickname());
                    if (!player.getLeaderCards().isEmpty()) {
                        System.out.println("LEADER CARDS:");
                        printLeaderCards(player);
                    }
                    if (!player.getDevelopmentCardSpace().isEmpty()) {
                        System.out.println("DEVELOPMENT CARDS:");
                        printDevelopmentCards(player);
                    }
                }
            }
        }
    }

    /**
     * Method to discard leaders from a specific player
     * @param indexLeaderCard1 the index of the first leader card to discard
     * @param indexLeaderCard2 the index of the second leader card to discard
     */
    public void discardInitialLeaders(String nickname, int indexLeaderCard1, int indexLeaderCard2) {
        players.stream().filter(x->x.getNickname().equals(nickname)).forEach(x->x.discardInitialLeaders(indexLeaderCard1,indexLeaderCard2));
        if(!players.contains(nickname))
            players.add(new PlayerCLI(nickname));
    }

    /**
     * Method to know the amount of resources that a player has to choose at the beginning of a match
     * @return an int based on the local player's turn
     */
    public int getNumOfResourceToChoose(){
        int turnOfPlayer = players.indexOf(localPlayer) ;
        switch(turnOfPlayer){
            case 0:
                return 0;
            case 1:
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                return 3;
        }
    }

    /**
     * This method updates the local model with the list of player in the right order received from the model
     * @param playersOrder a list of players nicknames
     */
    public void setPlayersOrder(List<String> playersOrder) {
        if(playersOrder.size() == 1)
            blackCrossPosition = 0;
        ArrayList <PlayerCLI> players = new ArrayList<>();
        for(String x : playersOrder){
            if (x.equals(localPlayer.getNickname()))
                players.add(localPlayer);
            else
                players.add(new PlayerCLI(x));
        }
        this.players = new ArrayList<>(players);
    }

    /**
     * Method to remove one card from a player's leader cards
     * @param leaderPosition the index of the card to discard
     * @param nickname the nickname of the player
     */
    public void removeLeaderCard(String nickname,int leaderPosition) {
        players.stream().filter(player -> player.getNickname().equals(nickname)).forEach(player -> player.removeCard(leaderPosition));
    }

    /**
     * Method to activate a player's leader card
     * @param numLeadercard the index of the leader card to activate
     * @param leaderCardID the ID of the card to activate
     * @param nickname the nickname of the player
     */
    public void activeLeaderCard(String nickname, int numLeadercard, int leaderCardID) {
        players.stream().filter(player -> player.getNickname().equals(nickname)).forEach(player -> player.activateCard(numLeadercard, leaderCardID));
    }

    /**
     * Setter to update the position of Lorenzo's Black cross
     * @param blackCrossPosition the updated position in Lorenzo's faith track
     */
    public void setBlackCrossPosition(int blackCrossPosition) {
        this.blackCrossPosition = blackCrossPosition;
    }

    /**
     * Method to print Lorenzo's faith track
     */
    private void printLorenzosFaithTrack(){
        String cell = "|";
        String blackCross = CliColor.COLOR_GREY+"┼"+ CliColor.RESET;
        String faithTrack = "";
        for (int pos = 0; pos<=24; pos++){
            if(blackCrossPosition == pos)
                faithTrack = faithTrack.concat(blackCross).concat(cell);
            else
                faithTrack = faithTrack.concat(String.valueOf(pos)).concat(cell);
        }
        System.out.println("Lorenzo's Faithtrack: \n"+ faithTrack);
    }

    /**
     * Method to generate all the cards of the game from the relative xml files that contains the cards
     */
    public void cliCardStringCreator(){
        String stringTemp = "";
        String string = "";
        int stringLength;

        LeaderCardParser leaderCardParser = new LeaderCardParser();
        Stack<LeaderDepot> leaderCardDepot = leaderCardParser.leaderCardDepotDeserializer();
        Stack<LeaderMarble> leaderCardMarble = leaderCardParser.leaderCardMarbleDeserializer();
        Stack<LeaderProduction> leaderCardProduction = leaderCardParser.leaderCardProductionDeserializer();
        Stack<LeaderDiscount> leaderCardDiscount = leaderCardParser.leaderCardDiscountDeserializer();

        Stack<DevelopmentCard>[][] cardGridMatrix;
        CardGridParser cardGridParser = new CardGridParser();
        cardGridMatrix = cardGridParser.parse();
        Stack<DevelopmentCard> cardGridMatrixStack;

        for(int i=0; i<maxRow; i++){
            for(int j=0; j<maxColumn; j++) {
                cardGridMatrixStack = cardGridMatrix[i][j];

                while (cardGridMatrixStack.size() != 0) {

                    string = "║";
                    stringLength = 1;
                    ArrayList <String> stringArray = new ArrayList<>();
                    stringArray.add("╔═══════════╗");//characters width: 15 ║
                    //codice di parsing della stringa che stampa la carta
                    DevelopmentCard developmentCard = cardGridMatrixStack.pop();
                    int id = developmentCard.getId();
                    int level = developmentCard.getLevel();
                    for (int k = 0; k < level; k++) {
                        stringTemp = getColorString.getColorDevelopmentCard(developmentCard.getColor()) + "*" + CliColor.RESET;
                        string = string.concat(stringTemp);
                        stringLength ++;
                    }
                    for(int addedCharacters = 0; addedCharacters < CARD_WIDTH - (stringLength + 1); addedCharacters++)
                        string = string.concat(" ");
                    string = string.concat("║");
                    //addSpaces(string);
                    stringArray.add(string);

                    string = "";
                    stringTemp = getMapResourceIntegerParser(developmentCard.getPrice());
                    string = string.concat(stringTemp);
                    //addSpaces(string);
                    stringArray.add(string);

                    string = "";
                    Map<Resource, Integer> cost = developmentCard.getPowerOfProduction().getCost();
                    Map<Resource, Integer> production = developmentCard.getPowerOfProduction().getProduction();

                    stringTemp = getCostProductionIntegerParser(cost,production);
                    string = string.concat(stringTemp);
                    //addSpaces(string);
                    stringArray.add(string);

                    string = "║";
                    stringLength = 1;
                    Integer victoryPoints = developmentCard.getVictoryPoints();
                    stringLength = stringLength + victoryPoints.toString().length();
                    stringTemp = String.valueOf(victoryPoints);
                    string = string.concat(stringTemp);
                    for(int addedCharacters = 0; addedCharacters < CARD_WIDTH - (stringLength + 1); addedCharacters++)
                        string = string.concat(" ");
                    string = string.concat("║");
                    //addSpaces(string);
                    stringArray.add(string);
                    stringArray.add("╚═══════════╝"); //characters width: 15 ║
                    cliCardString.put(id, stringArray);
                }
            }
        }

        while (leaderCardDepot.size() != 0) {
            ArrayList <String> stringArray = new ArrayList<>();
            stringArray.add("╔═══════════╗");//characters width: 15 ║

            //codice di parsing della stringa che stampa la carta
            LeaderDepot leaderDepot = leaderCardDepot.pop();
            int id = leaderDepot.getId();
            cardRequirementStringParser(leaderDepot, stringArray);

            Resource resource = leaderDepot.getSpecialDepotResource();
            string = "║";
            stringLength = 1;
            string = string + getColorString.getColorResource(resource) + "DEPOT" + CliColor.RESET;
            stringLength = stringLength + 5;
            for(int addedCharacters = 0; addedCharacters < CARD_WIDTH - (stringLength + 1); addedCharacters++)
                string = string.concat(" ");
            string = string.concat("║");
            //addSpaces(string);
            stringArray.add(string);
            stringArray.add("╚═══════════╝"); //characters width: 15 ║
            cliCardString.put(id, stringArray);
        }
        while (leaderCardDiscount.size() != 0) {
            string = "";
            ArrayList <String> stringArray = new ArrayList<>();
            stringArray.add("╔═══════════╗");//characters width: 15 ║

            //codice di parsing della stringa che stampa la carta
            LeaderDiscount leaderDiscount = leaderCardDiscount.pop();
            int id = leaderDiscount.getId();
            cardRequirementStringParser(leaderDiscount, stringArray);

            Resource resource = leaderDiscount.getResourceDiscounted();
            string = "║";
            stringLength = 1;
            string = string + getColorString.getColorResource(resource) + "DISCOUNT" + CliColor.RESET;
            stringLength = stringLength + 8;
            //addSpaces(string);
            for(int addedCharacters = 0; addedCharacters < CARD_WIDTH - (stringLength + 1); addedCharacters++)
                string = string.concat(" ");
            string = string.concat("║");
            stringArray.add(string);
            stringArray.add("╚═══════════╝"); //characters width: 15 ║
            cliCardString.put(id, stringArray);
        }

        while (leaderCardMarble.size() != 0) {
            string = "";
            ArrayList <String> stringArray = new ArrayList<>();
            stringArray.add("╔═══════════╗");//characters width: 15 ║

            //codice di parsing della stringa che stampa la carta
            LeaderMarble leaderMarble = leaderCardMarble.pop();
            int id = leaderMarble.getId();
            cardRequirementStringParser(leaderMarble, stringArray);

            Marble marble = leaderMarble.getMarble();
            string = "║";
            stringLength = 1;
            string = string + getColorString.getColorMarble(marble) + "MARBLE" + CliColor.RESET;
            stringLength = stringLength + 6;
            //addSpaces(string);
            for(int addedCharacters = 0; addedCharacters < CARD_WIDTH - (stringLength + 1); addedCharacters++)
                string = string.concat(" ");
            string = string.concat("║");
            stringArray.add(string);
            stringArray.add("╚═══════════╝"); //characters width: 15 ║
            cliCardString.put(id, stringArray);
        }
        while (leaderCardProduction.size() != 0) {
            string = "";
            ArrayList <String> stringArray = new ArrayList<>();
            stringArray.add("╔═══════════╗");//characters width: 15 ║

            //codice di parsing della stringa che stampa la carta
            LeaderProduction leaderProduction = leaderCardProduction.pop();
            int id = leaderProduction.getId();
            PowerOfProduction powerOfProduction = leaderProduction.abilityProduction();
            cardRequirementStringParser(leaderProduction, stringArray);
            cardPowerOfProductionStringParser(powerOfProduction, stringArray);

            stringArray.add("╚═══════════╝"); //characters width: 15 ║
            cliCardString.put(id, stringArray);
        }

        ArrayList <String> cardBackArray = new ArrayList<>();
        cardBackArray.add("╔═══════════╗");
        cardBackArray.add("║ CARD BACK ║");
        cardBackArray.add("║           ║");
        cardBackArray.add("║           ║");
        cardBackArray.add("╚═══════════╝");
        cliCardString.put(CARD_BACK, cardBackArray);

        ArrayList <String> emptyCardArray = new ArrayList<>();
        emptyCardArray.add("╔═══════════╗");
        emptyCardArray.add("║   EMPTY   ║");
        emptyCardArray.add("║   SPACE   ║");
        emptyCardArray.add("║           ║");
        emptyCardArray.add("║           ║");
        emptyCardArray.add("╚═══════════╝");
        cliCardString.put(EMPTY_CARD, emptyCardArray);
    }

    private ArrayList <String> cardRequirementStringParser(LeaderCard leaderCard, ArrayList <String> stringArray){
        String string = "";
        int stringLength = 0;
        String stringTemp;
        Requirement requirement = leaderCard.getRequirement();
        ArrayList<CardRequirement> cardRequirement = leaderCard.getRequirement().getCardsRequirement();
        if(cardRequirement != null) {
            string = getCardRequirementStringParser(cardRequirement);
            //addSpaces(string);
            stringArray.add(string);
        }

        string = "";
        Map<Resource, Integer> resourceRequirement = leaderCard.getRequirement().getResourceRequirement();
        if(resourceRequirement != null) {
            string = getMapResourceIntegerParser(resourceRequirement);
            //addSpaces(string);
            stringArray.add(string);
        }

        string = "║";
        int victoryPoints = leaderCard.getVictoryPoints();
        string = string + String.valueOf(victoryPoints);
        stringLength = string.length();
        //addSpaces(string);
        for(int addedCharacters = 0; addedCharacters < CARD_WIDTH - (stringLength + 1); addedCharacters++)
            string = string.concat(" ");
        string = string.concat("║");
        stringArray.add(string);
        return stringArray;
    }

    private ArrayList <String> cardPowerOfProductionStringParser(PowerOfProduction powerOfProduction, ArrayList <String> stringArray){
        String string = "║";
        int stringLength = 1;
        String stringTemp;
        Map<Resource, Integer> cost = powerOfProduction.getCost();
        Map<Resource,Integer> production = powerOfProduction.getProduction();
        //stringTemp = getMapResourceIntegerParser(cost);
        for(Resource x : Resource.values()){
            if(cost.get(x)!= null){
                stringTemp = cost.get(x) + "" + getColorString.getColorResource(x) + "■" + CliColor.RESET;
                string = string.concat(stringTemp);
                stringLength = stringLength + cost.get(x).toString().length() + 1;
            }
        }

        string = string.concat("=");
        stringLength++;
        //stringTemp = getMapResourceIntegerParser(production);
        for(Resource x : Resource.values()){
            if(cost.get(x)!= null){
                stringTemp = cost.get(x) + "" + getColorString.getColorResource(x) + "■" + CliColor.RESET;
                string = string.concat(stringTemp);
                stringLength = stringLength + cost.get(x).toString().length() + 1;
            }
        }

        string = string.concat("+?");
        stringLength = stringLength + 2;
        //addSpaces(string);
        for(int addedCharacters = 0; addedCharacters < CARD_WIDTH - (stringLength + 1); addedCharacters++)
            string = string.concat(" ");
        string = string.concat("║");
        stringArray.add(string);
        return stringArray;
    }

    private String getCardRequirementStringParser(ArrayList<CardRequirement> cardRequirement){
        String string = "║";
        int stringLength = 1;
        String level;
        while (!cardRequirement.isEmpty()) {
            CardRequirement x = cardRequirement.get(0);
            Integer freq = Collections.frequency(cardRequirement, x);

            if(x.getLevel()==0)
                level = "-";
            else level = Integer.toString(x.getLevel());
            stringLength = stringLength + level.length();

            String stringTemp = freq + "" + getColorString.getColorDevelopmentCard(x.getColor()) + "(" + level + ")" + "" + CliColor.RESET;
            stringLength = stringLength + freq.toString().length() + 2;
            string = string.concat(stringTemp);
            for (int i = 0; i < freq; i++)
                cardRequirement.remove(x);
        }
        for(int addedCharacters = 0; addedCharacters < CARD_WIDTH - (stringLength + 1); addedCharacters++)
            string = string.concat(" ");
        string = string.concat("║");
        return string;
    }

    private String getMapResourceIntegerParser(Map<Resource, Integer> map){
        String string = "║";
        int stringLength = 1;

        for(Resource x : Resource.values()){
            if(map.get(x)!= null){
                String stringTemp = map.get(x) + "" + getColorString.getColorResource(x) + "■" + CliColor.RESET;
                string = string.concat(stringTemp);
                stringLength = stringLength + map.get(x).toString().length() + 1;
            }
        }
        for(int addedCharacters = 0; addedCharacters < CARD_WIDTH - (stringLength + 1); addedCharacters++)
            string = string.concat(" ");
        string = string.concat("║");
        return string;
    }

    private String getCostProductionIntegerParser(Map<Resource, Integer> cost, Map<Resource, Integer> production) {
        String string = "║";
        int stringLength = 1;

        for(Resource x : Resource.values()){
            if(cost.get(x)!= null){
                String stringTemp = cost.get(x) + "" + getColorString.getColorResource(x) + "■" + CliColor.RESET;
                string = string.concat(stringTemp);
                stringLength = stringLength + cost.get(x).toString().length() + 1;
            }
        }

        string = string.concat("=");
        stringLength++;

        for(Resource x : Resource.values()){
            if(production.get(x)!= null){
                String stringTemp = production.get(x) + "" + getColorString.getColorResource(x) + "■" + CliColor.RESET;
                string = string.concat(stringTemp);
                stringLength = stringLength + production.get(x).toString().length() + 1;
            }
        }

        for(int addedCharacters = 0; addedCharacters < CARD_WIDTH - (stringLength + 1); addedCharacters++)
            string = string.concat(" ");
        string = string.concat("║");
        return string;
    }

    /**
     * Method to print the title o the game
     */
    public void printTitle() {
        System.out.println("\n" +
                " _______  _______  _______ _________ _______  _______  _______      _______  _______               \n" +
                "(       )(  ___  )(  ____ \\\\__   __/(  ____ \\(  ____ )(  ____ \\    (  ___  )(  ____ \\              \n" +
                "| () () || (   ) || (    \\/   ) (   | (    \\/| (    )|| (    \\/    | (   ) || (    \\/              \n" +
                "| || || || (___) || (_____    | |   | (__    | (____)|| (_____     | |   | || (__                  \n" +
                "| |(_)| ||  ___  |(_____  )   | |   |  __)   |     __)(_____  )    | |   | ||  __)                 \n" +
                "| |   | || (   ) |      ) |   | |   | (      | (\\ (         ) |    | |   | || (                    \n" +
                "| )   ( || )   ( |/\\____) |   | |   | (____/\\| ) \\ \\__/\\____) |    | (___) || )                    \n" +
                "|/     \\||/     \\|\\_______)   )_(   (_______/|/   \\__/\\_______)    (_______)|/                     \n" +
                "                                                                                                   \n" +
                " _______  _______ _________ _        _______  _______  _______  _______  _        _______  _______ \n" +
                "(  ____ )(  ____ \\\\__   __/( (    /|(  ____ \\(  ____ \\(  ____ \\(  ___  )( (    /|(  ____ \\(  ____ \\\n" +
                "| (    )|| (    \\/   ) (   |  \\  ( || (    \\/| (    \\/| (    \\/| (   ) ||  \\  ( || (    \\/| (    \\/\n" +
                "| (____)|| (__       | |   |   \\ | || (__    | (_____ | (_____ | (___) ||   \\ | || |      | (__    \n" +
                "|     __)|  __)      | |   | (\\ \\) ||  __)   (_____  )(_____  )|  ___  || (\\ \\) || |      |  __)   \n" +
                "| (\\ (   | (         | |   | | \\   || (            ) |      ) || (   ) || | \\   || |      | (      \n" +
                "| ) \\ \\__| (____/\\___) (___| )  \\  || (____/\\/\\____) |/\\____) || )   ( || )  \\  || (____/\\| (____/\\\n" +
                "|/   \\__/(_______/\\_______/|/    )_)(_______/\\_______)\\_______)|/     \\||/    )_)(_______/(_______/\n" +
                "                                                                                                   \n");
    }
}
