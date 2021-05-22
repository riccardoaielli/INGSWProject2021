package it.polimi.ingsw.client.LocalModel;

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
    private ArrayList <Player> players;
    private Player localPlayer;
    private String currentPlayer;
    private Market market;
    private CardGrid cardGrid;
    private FaithTrack faithTrack;
    private DevelopmentCardSpace developmentCardSpace;
    private WareHouseDepots wareHouseDepots;
    private Strongbox strongbox;
    private Map<Integer,ArrayList <String>> cliCardString = new HashMap<>();
    private GetColorString getColorString = new GetColorString();
    private final int maxRow = 3, maxColumn = 4;

    int[][] cardGridMatrix;
    ArrayList<String> stringArray = new ArrayList<>();
    int k;


    public LocalModel() {
        players = new ArrayList<>();
        market = new Market();
        cardGrid = new CardGrid();
        faithTrack = new FaithTrack();
        developmentCardSpace = new DevelopmentCardSpace();
        wareHouseDepots = new WareHouseDepots();
        strongbox = new Strongbox();
        cliCardStringCreator();
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    public void addPlayer(String Nickname){

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
     * @param initialLeaderCards a list of cards
     */
    public void setInitialLeaderCards(ArrayList<Integer> initialLeaderCards){
        players.stream().filter(x->x.getNickname().equals(localPlayer.getNickname())).forEach(x->x.setLeaderCards(initialLeaderCards));
    }


    public Player getPlayer(String nickname){
       for(Player player: players){
           if (player.getNickname().equals(nickname))
               return player;
       }
       return null;
    }

    public void printCard(int x) {
        ArrayList<String> arrayList = cliCardString.get(x);
        for(int k=0; k<arrayList.size(); k++)
            System.out.print(arrayList.get(k) + "\n");
    }

    public void printMarket(){
        market.printMarket();
    }

    public void printCardGrid(){
        int k = 0;
        for(int i=0; i<maxRow; i++) {
                for (int j = 0; j < maxColumn; j++) {
                        int x = cardGridMatrix[i][j];
                        printCard(x);
                }
        }
    }

    public void printLeaderCards() {
        ArrayList <Integer> leaderCard = localPlayer.getLeaderCards();
        for(Integer x : leaderCard)
        this.printCard(x);
    }

    public void setLocalPlayer(String localPlayer) {
        this.localPlayer = new Player(localPlayer);
        players.add(this.localPlayer);
    }

    public void printView(){
        printMarket();
        printCardGrid();
        printLeaderCards();
        for(Player player : players)
            player.printPersonalBoards();
    }

    public void discardInitialLeaders(String nickname, int indexLeaderCard1, int indexLeaderCard2) {
        players.stream().filter(x->x.getNickname().equals(nickname)).forEach(x->x.discardInitialLeaders(indexLeaderCard1,indexLeaderCard2));
    }

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

    public void setPlayersOrder(List<String> playersOrder) {
        ArrayList <Player> players = new ArrayList<>();
        for(String x : playersOrder){
            if (x.equals(localPlayer.getNickname()))
                players.add(this.players.get(0));
            else
                players.add(new Player(x));
        }
        this.players = new ArrayList<>(players);
    }

    public void cliCardStringCreator(){

        String stringTemp = "";
        String string = "";

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

                    string = "";
                    ArrayList <String> stringArray = new ArrayList<>();
                    //codice di parsing della stringa che stampa la carta
                    DevelopmentCard developmentCard = cardGridMatrixStack.pop();
                    int id = developmentCard.getId();
                    int level = developmentCard.getLevel();
                    for (int k = 0; k < level; k++) {
                        stringTemp = getColorString.getColorDevelopmentCard(developmentCard.getColor()) + "♦" + cliColor.RESET;
                        string = string.concat(stringTemp);
                    }
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
                    stringTemp = getMapResourceIntegerParser(cost);
                    stringTemp = stringTemp.concat("=");
                    string = string.concat(stringTemp);
                    stringTemp = getMapResourceIntegerParser(production);
                    string = string.concat(stringTemp);
                    //addSpaces(string);
                    stringArray.add(string);

                    string = "";
                    int victoryPoints = developmentCard.getVictoryPoints();
                    stringTemp = String.valueOf(victoryPoints);
                    string = string.concat(stringTemp);
                    //addSpaces(string);
                    stringArray.add(string);

                    /*for(String x : stringArray)
                        System.out.print(x + "\n");*/

                    cliCardString.put(id, stringArray);
                }
            }
        }

        while (leaderCardDepot.size() != 0) {

            string = "";
            ArrayList <String> stringArray = new ArrayList<>();
            //codice di parsing della stringa che stampa la carta
            LeaderDepot leaderDepot = leaderCardDepot.pop();
            int id = leaderDepot.getId();
            cardRequirementStringParser(leaderDepot, stringArray);

            Resource resource = leaderDepot.getSpecialDepotResource();
            string = "";
            string = getColorString.getColorResource(resource) + "DEPOT" + cliColor.RESET;
            //addSpaces(string);
            stringArray.add(string);

            /*for(String x : stringArray)
                System.out.print(x + "\n");*/
            cliCardString.put(id, stringArray);
        }

        while (leaderCardDiscount.size() != 0) {

            string = "";
            ArrayList <String> stringArray = new ArrayList<>();
            //codice di parsing della stringa che stampa la carta
            LeaderDiscount leaderDiscount = leaderCardDiscount.pop();
            int id = leaderDiscount.getId();
            cardRequirementStringParser(leaderDiscount, stringArray);

            Resource resource = leaderDiscount.getResourceDiscounted();
            string = "";
            string = getColorString.getColorResource(resource) + "DISCOUNT" + cliColor.RESET;
            //addSpaces(string);
            stringArray.add(string);

            /*for(String x : stringArray)
                System.out.print(x + "\n");*/
            cliCardString.put(id, stringArray);
        }

        while (leaderCardMarble.size() != 0) {

            string = "";
            ArrayList <String> stringArray = new ArrayList<>();
            //codice di parsing della stringa che stampa la carta
            LeaderMarble leaderMarble = leaderCardMarble.pop();
            int id = leaderMarble.getId();
            cardRequirementStringParser(leaderMarble, stringArray);

            Marble marble = leaderMarble.getMarble();
            string = "";
            string = getColorString.getColorMarble(marble) + "MARBLE" + cliColor.RESET;
            //addSpaces(string);
            stringArray.add(string);

            /*for(String x : stringArray)
                System.out.print(x + "\n");*/
            cliCardString.put(id, stringArray);
        }

        while (leaderCardProduction.size() != 0) {

            string = "";
            ArrayList <String> stringArray = new ArrayList<>();
            //codice di parsing della stringa che stampa la carta
            LeaderProduction leaderProduction = leaderCardProduction.pop();
            int id = leaderProduction.getId();
            PowerOfProduction powerOfProduction = leaderProduction.getPowerOfProduction();

            cardRequirementStringParser(leaderProduction, stringArray);


            cardPowerOfProductionStringParser(powerOfProduction, stringArray);

            /*for(String x : stringArray)
                System.out.print(x + "\n");*/
            cliCardString.put(id, stringArray);
        }
    }

    /*private String addSpaces(String string){
        String str = string;
        int space =100 - str.length();
        for (int k = 0; k < space; k++) {
            str = str.concat("si");
        }
        str = str.concat("ok");
        return str;
    }*/

    private ArrayList <String> cardRequirementStringParser(LeaderCard leaderCard, ArrayList <String> stringArray){

        String string = "";
        String stringTemp;
        Requirement requirement = leaderCard.getRequirement();
        ArrayList<CardRequirement> cardRequirement = leaderCard.getRequirement().getCardsRequirement();
        if(cardRequirement != null) {
            stringTemp = getCardRequirementStringParser(cardRequirement);
            string = string.concat(stringTemp);
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

        string = "";
        int victoryPoints = leaderCard.getVictoryPoints();
        string = String.valueOf(victoryPoints);
        //addSpaces(string);
        stringArray.add(string);
        return stringArray;
    }

    private ArrayList <String> cardPowerOfProductionStringParser(PowerOfProduction powerOfProduction, ArrayList <String> stringArray){

        String string = "";
        String stringTemp;
        Map<Resource, Integer> cost = powerOfProduction.getCost();
        Map<Resource,Integer> production = powerOfProduction.getProduction();

        stringTemp = getMapResourceIntegerParser(cost);
        stringTemp = stringTemp.concat("=");
        string = string.concat(stringTemp);

        stringTemp = getMapResourceIntegerParser(production);
        stringTemp = stringTemp.concat("+?");
        string = string.concat(stringTemp);
        //addSpaces(string);
        stringArray.add(string);

        return stringArray;
    }

    private String getCardRequirementStringParser(ArrayList<CardRequirement> cardRequirement){

        String string = "";
        String level;
        while (!cardRequirement.isEmpty()) {
            CardRequirement x = cardRequirement.get(0);
            int freq = Collections.frequency(cardRequirement, x);

            if(x.getLevel()==0)
                level = "-";
            else level = Integer.toString(x.getLevel());

            String stringTemp = freq + "" + getColorString.getColorDevelopmentCard(x.getColor()) + "(" + level + ")" + "" + cliColor.RESET;
            string = string.concat(stringTemp);
            for (int i = 0; i < freq; i++)
                cardRequirement.remove(x);
        }
        return string;
    }

    private String getMapResourceIntegerParser(Map<Resource, Integer> map){

        String string = "";
        for(Resource x : Resource.values()){
            if(map.get(x)!= null){
                String stringTemp = map.get(x) + "" + getColorString.getColorResource(x) + "■" + cliColor.RESET;
                string = string.concat(stringTemp);
            }
        }
        return string;
    }
}
