package it.polimi.ingsw.common;

import com.google.gson.*;
import it.polimi.ingsw.common.*;

import java.lang.reflect.Type;

public class MessageDeserializer implements JsonDeserializer{
    private GsonBuilder gsonBuilder;
    private Gson customGson;

    public MessageDeserializer() {
        gsonBuilder = new GsonBuilder().registerTypeAdapter(MessageToServer.class,this);
        customGson = gsonBuilder.create();
    }


    @Override
    public MessageToServer deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Gson gson = new Gson();

        switch (jsonObject.get("messageType").getAsString()){
            case "NICKNAME_REPLY":
                return gson.fromJson(jsonObject, NicknameReplyMessage.class);
            case "DISCARD_INITIAL_LEADER":
                return gson.fromJson(jsonObject, DiscardInitialLeaderMessage.class);
            case "CHOOSE_INITIAL_RESOURCES":
                return gson.fromJson(jsonObject, ChooseInitialResourcesMessage.class);
            case "CREATE_MESSAGE_REPLY":
                return gson.fromJson(jsonObject, CreateMatchReplyMessage.class);
            case "TAKE_FROM_MARKET":
                return gson.fromJson(jsonObject, TakeFromMarketMessage.class);
            case "TRANSFORM_WHITE_MARBLES":
                return gson.fromJson(jsonObject, TransformWhiteMarblesMessage.class);
            case "TRANSFORM_MARBLES":
                return gson.fromJson(jsonObject, TransformMarblesMessage.class);
        }

        return null;
    }

    public MessageToServer deserializeMessage(String serializedMessage){
        return customGson.fromJson(serializedMessage, MessageToServer.class);
    }
}
