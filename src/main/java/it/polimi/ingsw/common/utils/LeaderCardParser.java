package it.polimi.ingsw.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.server.model.*;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Stack;

/**
 * this class loads all the leader cards from the resources of the model
 */
public class LeaderCardParser {

    private Stack<LeaderCard> leaderCards;

    private static final String pathLeaderCardDepot = "/server/leaderCardDepot.json";
    private static final String pathLeaderCardDiscount = "/server/leaderCardDiscount.json";
    private static final String pathLeaderCardMarble = "/server/leaderCardMarble.json";
    private static final String pathLeaderCardProduction = "/server/leaderCardProduction.json";

    Reader reader = null;
    Gson gson = new Gson();

    public Stack<LeaderCard> loadLeaderCards(){

        Stack<LeaderDepot> leaderCardDepot = leaderCardDepotDeserializer();
        Stack<LeaderMarble> leaderCardMarble = leaderCardMarbleDeserializer();
        Stack<LeaderProduction> leaderCardProduction = leaderCardProductionDeserializer();
        Stack<LeaderDiscount> leaderCardDiscount = leaderCardDiscountDeserializer();

        leaderCards = new Stack<>();

        //Creating one list with all leader cards
        // Push contents from all leader stacks in leaderCards stack
        while (leaderCardDepot.size() != 0) {
            leaderCards.push(leaderCardDepot.pop());
        }

        while (leaderCardDiscount.size() != 0) {
            leaderCards.push(leaderCardDiscount.pop());
        }

        while (leaderCardMarble.size() != 0) {
            leaderCards.push(leaderCardMarble.pop());
        }

        while (leaderCardProduction.size() != 0) {
            leaderCards.push(leaderCardProduction.pop());
        }
        return leaderCards;
    }

    public Stack<LeaderDepot> leaderCardDepotDeserializer(){

        Stack<LeaderDepot> leaderCardDepot;

        //Deserializing leaderCardDepot
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream(pathLeaderCardDepot)));

        Type myDataType = new TypeToken<Stack<LeaderDepot>>(){}.getType();
        leaderCardDepot = gson.fromJson(reader, myDataType);

        return leaderCardDepot;
    }

    public Stack<LeaderMarble> leaderCardMarbleDeserializer(){

        Stack<LeaderMarble> leaderCardMarble;

        //Deserializing leaderCardMarble
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream(pathLeaderCardMarble)));

        Type myDataType = new TypeToken<Stack<LeaderMarble>>(){}.getType();
        leaderCardMarble = gson.fromJson(reader, myDataType);

        return leaderCardMarble;
    }

    public Stack<LeaderProduction> leaderCardProductionDeserializer(){

        Stack<LeaderProduction> leaderCardProduction;

        //Deserializing leaderCardProduction
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream(pathLeaderCardProduction)));

        Type myDataType = new TypeToken<Stack<LeaderProduction>>(){}.getType();
        leaderCardProduction = gson.fromJson(reader, myDataType);

        return leaderCardProduction;
    }

    public Stack<LeaderDiscount> leaderCardDiscountDeserializer(){

        Stack<LeaderDiscount> leaderCardDiscount;

        //Deserializing leaderCardDiscount
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream(pathLeaderCardDiscount)));

        Type myDataType = new TypeToken<Stack<LeaderDiscount>>(){}.getType();
        leaderCardDiscount = gson.fromJson(reader, myDataType);

        return leaderCardDiscount;
    }

}
