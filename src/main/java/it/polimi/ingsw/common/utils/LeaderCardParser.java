package it.polimi.ingsw.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.server.model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Stack;

/**
 * this class loads all the leader cards from the resources of the model
 */
public class LeaderCardParser {

    private Stack<LeaderCard> leaderCards;

    private static final String pathLeaderCardDepot = "src/main/resources/server/leaderCardDepot.json";
    private static final String pathLeaderCardDiscount = "src/main/resources/server/leaderCardDiscount.json";
    private static final String pathLeaderCardMarble = "src/main/resources/server/leaderCardMarble.json";
    private static final String pathLeaderCardProduction = "src/main/resources/server/leaderCardProduction.json";

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
        try {
            reader = new FileReader(pathLeaderCardDepot);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Type myDataType = new TypeToken<Stack<LeaderDepot>>(){}.getType();
        leaderCardDepot = gson.fromJson(reader, myDataType);

        return leaderCardDepot;
    }

    public Stack<LeaderMarble> leaderCardMarbleDeserializer(){

        Stack<LeaderMarble> leaderCardMarble;

        //Deserializing leaderCardMarble
        try {
            reader = new FileReader(pathLeaderCardMarble);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Type myDataType = new TypeToken<Stack<LeaderMarble>>(){}.getType();
        leaderCardMarble = gson.fromJson(reader, myDataType);

        return leaderCardMarble;
    }

    public Stack<LeaderProduction> leaderCardProductionDeserializer(){

        Stack<LeaderProduction> leaderCardProduction;

        //Deserializing leaderCardProduction
        try {
            reader = new FileReader(pathLeaderCardProduction);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Type myDataType = new TypeToken<Stack<LeaderProduction>>(){}.getType();
        leaderCardProduction = gson.fromJson(reader, myDataType);

        return leaderCardProduction;
    }

    public Stack<LeaderDiscount> leaderCardDiscountDeserializer(){

        Stack<LeaderDiscount> leaderCardDiscount;

        //Deserializing leaderCardDiscount
        try {
            reader = new FileReader(pathLeaderCardDiscount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Type myDataType = new TypeToken<Stack<LeaderDiscount>>(){}.getType();
        leaderCardDiscount = gson.fromJson(reader, myDataType);

        return leaderCardDiscount;
    }

}
