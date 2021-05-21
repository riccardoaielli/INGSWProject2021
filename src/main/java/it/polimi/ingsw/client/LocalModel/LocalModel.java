package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.common.utils.LeaderCardParser;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;
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
    private Map<Integer,String> cliCardString = new HashMap<>();
    private GetColorString getColorString = new GetColorString();


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
        System.out.println(cliCardString.get(x));
    }

    public void printMarket(){
        market.printMarket();
    }

    public void printCardGrid(){
        //System.out.println(cardGrid.toString());
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
                return 1;
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

        String stringTemp;
        String string;

        LeaderCardParser leaderCardParser = new LeaderCardParser();
        Stack<LeaderDepot> leaderCardDepot = leaderCardParser.leaderCardDepotDeserializer();
        Stack<LeaderMarble> leaderCardMarble = leaderCardParser.leaderCardMarbleDeserializer();
        Stack<LeaderProduction> leaderCardProduction = leaderCardParser.leaderCardProductionDeserializer();
        Stack<LeaderDiscount> leaderCardDiscount = leaderCardParser.leaderCardDiscountDeserializer();

        while (leaderCardDepot.size() != 0) {

            string = "";
            //codice di parsing della stringa che stampa la carta
            LeaderDepot leaderDepot = leaderCardDepot.pop();
            int id = leaderDepot.getId();
            stringTemp = cardRequirementStringParser(leaderDepot);
            string = string.concat(stringTemp);
            Resource resource = leaderDepot.getSpecialDepotResource();
            stringTemp = getColorString.getColorResource(resource) + "DEPOT" + "\n" + cliColor.RESET;
            string = string.concat(stringTemp);

            //System.out.print(string);
            cliCardString.put(id, string);
        }

        while (leaderCardDiscount.size() != 0) {

            string = "";
            //codice di parsing della stringa che stampa la carta
            LeaderDiscount leaderDiscount = leaderCardDiscount.pop();
            int id = leaderDiscount.getId();
            stringTemp = cardRequirementStringParser(leaderDiscount);
            string = string.concat(stringTemp);
            Resource resource = leaderDiscount.getResourceDiscounted();
            stringTemp = getColorString.getColorResource(resource) + "DISCOUNT" + "\n" + cliColor.RESET;
            string = string.concat(stringTemp);

            //System.out.print(string);
            cliCardString.put(id, string);
        }

        while (leaderCardMarble.size() != 0) {

            string = "";
            //codice di parsing della stringa che stampa la carta
            LeaderMarble leaderMarble = leaderCardMarble.pop();
            int id = leaderMarble.getId();
            stringTemp = cardRequirementStringParser(leaderMarble);
            string = string.concat(stringTemp);
            Marble marble = leaderMarble.getMarble();
            stringTemp = getColorString.getColorMarble(marble) + "MARBLE" + "\n" + cliColor.RESET;
            string = string.concat(stringTemp);

            //System.out.print(string);
            cliCardString.put(id, string);
        }

        while (leaderCardProduction.size() != 0) {

            string = "";
            //codice di parsing della stringa che stampa la carta
            LeaderProduction leaderProduction = leaderCardProduction.pop();
            int id = leaderProduction.getId();
            stringTemp = cardRequirementStringParser(leaderProduction);
            string = string.concat(stringTemp);

            stringTemp = cardPowerOfProductionStringParser(leaderProduction);
            string = string.concat(stringTemp);

            stringTemp = "PRODUCT" + "\n" + cliColor.RESET;
            string = string.concat(stringTemp);

            //System.out.print(string);
            cliCardString.put(id, string);
        }
        //System.out.println("...");
    }

    private String cardRequirementStringParser(LeaderCard leaderCard){
        String string = "";
        String stringTemp;
        Requirement requirement = leaderCard.getRequirement();
        ArrayList<CardRequirement> cardRequirement = leaderCard.getRequirement().getCardsRequirement();
        if(cardRequirement != null) {
            stringTemp = getCardRequirementStringParser(cardRequirement);
            string = string.concat(stringTemp);
        }
        Map<Resource, Integer> resourceRequirement = leaderCard.getRequirement().getResourceRequirement();
        if(resourceRequirement != null) {
            stringTemp = getMapResourceIntegerParser(resourceRequirement);
            stringTemp = stringTemp.concat("\n");
            string = string.concat(stringTemp);
        }
        int victoryPoints = leaderCard.getVictoryPoints();
        stringTemp = String.valueOf(victoryPoints) + "\n";
        string = string.concat(stringTemp);
        return string;
    }

    private String cardPowerOfProductionStringParser(LeaderProduction leaderProduction){

        String string = "";
        String stringTemp;
        PowerOfProduction powerOfProduction = leaderProduction.getPowerOfProduction();
        Map<Resource, Integer> cost = powerOfProduction.getCost();
        Map<Resource,Integer> production = powerOfProduction.getProduction();

        stringTemp = getMapResourceIntegerParser(cost);
        stringTemp = stringTemp.concat("=");
        string = string.concat(stringTemp);

        stringTemp = getMapResourceIntegerParser(production);
        stringTemp = stringTemp.concat("+?");
        string = string.concat(stringTemp);

        string = string.concat("\n");
        return string;
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
        string = string.concat("\n");
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
