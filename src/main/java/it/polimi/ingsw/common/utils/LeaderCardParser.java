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
    private Stack<LeaderCard> leaderCardDepot;
    private Stack<LeaderCard> leaderCardMarble;
    private Stack<LeaderCard> leaderCardDiscount;
    private Stack<LeaderCard> leaderCardProduction;

    public Stack<LeaderCard> loadLeaderCards(){

        Gson gson = new Gson();

        String pathLeaderCardDepot = "src/main/resources/server/leaderCardDepot.json";
        String pathLeaderCardDiscount = "src/main/resources/server/leaderCardDiscount.json";
        String pathLeaderCardMarble = "src/main/resources/server/leaderCardMarble.json";
        String pathLeaderCardProduction = "src/main/resources/server/leaderCardProduction.json";

        Reader reader = null;

        leaderCards = new Stack<>();

        //Deserializing leaderCardDepot
        try {
            reader = new FileReader(pathLeaderCardDepot);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Type myDataType = new TypeToken<Stack<LeaderDepot>>(){}.getType();
        leaderCardDepot = gson.fromJson(reader, myDataType);

        //Deserializing leaderCardDiscount
        try {
            reader = new FileReader(pathLeaderCardDiscount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        myDataType = new TypeToken<Stack<LeaderDiscount>>(){}.getType();
        leaderCardDiscount = gson.fromJson(reader, myDataType);

        //Deserializing leaderCardMarble
        try {
            reader = new FileReader(pathLeaderCardMarble);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        myDataType = new TypeToken<Stack<LeaderMarble>>(){}.getType();
        leaderCardMarble = gson.fromJson(reader, myDataType);

        //Deserializing leaderCardProduction
        try {
            reader = new FileReader(pathLeaderCardProduction);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        myDataType = new TypeToken<Stack<LeaderProduction>>(){}.getType();
        leaderCardProduction = gson.fromJson(reader, myDataType);

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
}
