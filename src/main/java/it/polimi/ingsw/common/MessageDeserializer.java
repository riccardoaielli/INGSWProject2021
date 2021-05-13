package it.polimi.ingsw.common;

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * This class offers a custom deserializer to deserialize a json string into a massage without knowing the type of the message
 */
public class MessageDeserializer implements JsonDeserializer{
    private GsonBuilder gsonBuilder;
    private Gson customGson;

    /**
     * This constructor creates the custom gsonBuilder to deserialize the messages
     */
    public MessageDeserializer() {
        gsonBuilder = new GsonBuilder().registerTypeAdapter(MessageToServer.class,this);
        customGson = gsonBuilder.create();
    }


    /**
     * This is the custom deserializer that deserializes a massage in a specific type of massage reading its type from the json
     * @param json is the json message to deserialize
     * @param type is the class type to deserialize
     * @param jsonDeserializationContext is the deserialization context
     * @return a message that extends MessageToServer
     * @throws JsonParseException the parsing goes wrong
     */
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

    /**
     *This method deserializes a string into a massage of a specific type that extends MessageToServer
     * @param serializedMessage is a String with the json massage to deserialize
     * @return a massage of a specific type that extends MessageToServer
     */
    public MessageToServer deserializeMessage(String serializedMessage){
        return customGson.fromJson(serializedMessage, MessageToServer.class);
    }
}
