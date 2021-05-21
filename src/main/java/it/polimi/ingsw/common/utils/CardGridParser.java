package it.polimi.ingsw.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.server.model.DevelopmentCard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Stack;



public class CardGridParser {

    private Stack<DevelopmentCard>[][] cardGridMatrix;
    Gson gson = new Gson();

    public Stack<DevelopmentCard>[][] parse() {

    String path = "src/main/resources/server/developmentCards.json";

    Reader reader = null;
    try {
        reader = new FileReader(path);
    } catch (
            FileNotFoundException e) {
        e.printStackTrace();
    }

    Type myDataType = new TypeToken<Stack<DevelopmentCard>[][]>() {}.getType();
    cardGridMatrix = gson.fromJson(reader, myDataType);
    return cardGridMatrix;
    }

}
