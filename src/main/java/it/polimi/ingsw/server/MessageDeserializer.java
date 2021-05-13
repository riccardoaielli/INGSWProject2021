package it.polimi.ingsw.server;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.common.*;

import java.lang.reflect.Type;

public class MessageDeserializer implements JsonDeserializer {
    @Override
    public Message deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Gson gson = new Gson();

        switch (jsonObject.get("messageType").getAsString()){
            case "NICKNAME_REPLY":
                return gson.fromJson(jsonObject, NicknameReplyMessage.class);
            case "DISCARD_INITIAL_LEADER":
                return gson.fromJson(jsonObject, DiscardInitialLeaderMessage.class);
            case "CHOOSE_INITIAL_RESOURCES":
                return gson.fromJson(jsonObject, ChooseInitialResourcesMessage.class);
        }

        return null;
    }
}
