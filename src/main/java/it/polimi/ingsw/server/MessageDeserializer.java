package it.polimi.ingsw.server;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.common.MessageType;
import it.polimi.ingsw.common.NicknameReplyMessage;
import it.polimi.ingsw.model.Message;
import it.polimi.ingsw.model.Messaggio1;
import it.polimi.ingsw.model.Messaggio2;
import it.polimi.ingsw.model.enumProva;

import java.lang.reflect.Type;

public class MessageDeserializer implements JsonDeserializer {
    @Override
    public Message deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Gson gson = new Gson();

        switch (jsonObject.get("messageType").getAsString()){
            case "NICKNAME_REPLY":
                Type massageType = new TypeToken<NicknameReplyMessage>(){}.getType();
                return gson.fromJson(jsonObject, massageType);
        }

        return null;
    }
}
