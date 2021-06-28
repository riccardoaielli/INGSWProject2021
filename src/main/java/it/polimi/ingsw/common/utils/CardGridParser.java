package it.polimi.ingsw.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.server.model.DevelopmentCard;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Stack;



public class CardGridParser {

    private Stack<DevelopmentCard>[][] cardGridMatrix;
    Gson gson = new Gson();

    public Stack<DevelopmentCard>[][] parse() {

    String path = "/server/developmentCards.json";
    InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream(path)));


    Type myDataType = new TypeToken<Stack<DevelopmentCard>[][]>() {}.getType();
    cardGridMatrix = gson.fromJson(reader, myDataType);
    return cardGridMatrix;
    }

}
