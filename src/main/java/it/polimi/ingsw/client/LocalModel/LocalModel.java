package it.polimi.ingsw.client.LocalModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

import javax.swing.text.html.HTMLDocument;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;
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
    private Map<String, String> cliCardString;

    private final String COLOR_BLUE = "\033[38;5;12m";
    private final String COLOR_YELLOW = "\033[38;5;11m";
    private final String COLOR_GREY = "\033[38;5;8m";
    private final String COLOR_PURPLE = "\033[38;5;5m";
    private final String COLOR_GREEN = "\033[38;5;10m";
    private final String COLOR_WHITE = "\033[38;5;15m";
    private final String COLOR_RED = "\033[38;5;9m";
    private final String RESET = "\033[0m";


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



    public void printMarket(){
        /*System.out.println(marketMatrix);
        for(int i=0; i<marketMatrix.size(); i++)
            for(Marble[] marbleElem : marketMatrix)*/
    }
    public void printCardGrid(){
        System.out.println(cardGrid.toString());
    }


    public void printView(){

    }

    public void printLeaderCards() {
        System.out.println(localPlayer + ": stampare carte leader del giocatore locale");
    }

    public void setLocalPlayer(String localPlayer) {
        this.localPlayer = new Player(localPlayer);
        players.add(this.localPlayer);
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

        String pathLeaderCardDepot = "src/main/resources/server/leaderCardDepot.json";
        String pathLeaderCardDiscount = "src/main/resources/server/leaderCardDiscount.json";
        String pathLeaderCardMarble = "src/main/resources/server/leaderCardMarble.json";
        String pathLeaderCardProduction = "src/main/resources/server/leaderCardProduction.json";

        Reader reader = null;

        try {
            reader = new FileReader(pathLeaderCardDepot);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
        for(JsonElement jsonElement : jsonArray){
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int id = jsonObject.get("id").getAsInt();
            int victoryPoints = jsonObject.get("victoryPoints").getAsInt();
            Map<Resource, Integer> resourceRequirement = jsonObject.get("resourceRequirement").get;
            System.out.println( + "...");
            System.out.println(id + "...");
        }*/




        //jsonObject.get("messageType").getAsString();
        System.out.println("...");

    }
}
