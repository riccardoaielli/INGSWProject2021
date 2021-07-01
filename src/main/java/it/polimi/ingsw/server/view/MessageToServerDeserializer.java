package it.polimi.ingsw.server.view;

import com.google.gson.*;
import it.polimi.ingsw.common.messages.messagesToServer.MessageToServer;
import it.polimi.ingsw.common.messages.messagesToServer.*;

import java.lang.reflect.Type;

/**
 * This class offers a custom deserializer to deserialize a json string into a massage without knowing the type of the message
 */
public class MessageToServerDeserializer implements JsonDeserializer <MessageToServer>{
    private GsonBuilder gsonBuilder;
    private Gson customGson;

    /**
     * This constructor creates the custom gsonBuilder to deserialize the messages
     */
    public MessageToServerDeserializer() {
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
            case "CREATE_MATCH_REPLY":
                return gson.fromJson(jsonObject, CreateMatchReplyMessage.class);
            case "TAKE_FROM_MARKET":
                return gson.fromJson(jsonObject, TakeFromMarketMessage.class);
            case "TRANSFORM_WHITE_MARBLES":
                return gson.fromJson(jsonObject, TransformWhiteMarblesMessage.class);
            case "TRANSFORM_MARBLES":
                return gson.fromJson(jsonObject, TransformMarblesMessage.class);
            case "ACTIVATE_LEADER":
                return gson.fromJson(jsonObject, ActivateLeaderMessage.class);
            case "ADD_TO_WAREHOUSE" :
                return gson.fromJson(jsonObject, AddToWarehouseMessage.class);
            case "BUY_DEVELOPMENT_CARD" :
                return gson.fromJson(jsonObject, BuyDevelopmentCardMessage.class);
            case "SWAP":
                return gson.fromJson(jsonObject, SwapMessage.class);
            case "MOVE":
                return gson.fromJson(jsonObject, MoveMessage.class);
            case "DISCARD_LEADER":
                return gson.fromJson(jsonObject, DiscardLeaderMessage.class);
            case "ACTIVATE_CARD_PRODUCTION":
                return gson.fromJson(jsonObject, ActivateCardProductionMessage.class);
            case "ACTIVATE_BASIC_PRODUCTION":
                return gson.fromJson(jsonObject, ActivateBasicProductionMessage.class);
            case "ACTIVATE_LEADER_PRODUCTION":
                return gson.fromJson(jsonObject, ActivateLeaderProductionMessage.class);
            case "END_PRODUCTION":
                return gson.fromJson(jsonObject, EndProduction.class);
            case "END_TURN":
                return gson.fromJson(jsonObject, EndTurnMessage.class);
            case "DISCARD_RESOURCES_FROM_MARKET":
                return gson.fromJson(jsonObject, DiscardResourcesFromMarketMessage.class);
            case "PING":
                return gson.fromJson(jsonObject, PingMessage.class);
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
