package it.polimi.ingsw.client;

import com.google.gson.*;
import it.polimi.ingsw.common.messages.messagesToClient.*;
import it.polimi.ingsw.common.messages.messagesToClient.ErrorMessage;

import java.lang.reflect.Type;

/**
 * This class offers a custom deserializer to deserialize a json string into a massage without knowing the type of the message
 */
public class MessageToClientDeserializer implements JsonDeserializer <MessageToClient>{
    private GsonBuilder gsonBuilder;
    private Gson customGson;

    /**
     * This constructor creates the custom gsonBuilder to deserialize the messages
     */
    public MessageToClientDeserializer() {
        gsonBuilder = new GsonBuilder().registerTypeAdapter(MessageToClient.class,this);
        customGson = gsonBuilder.create();
    }


    /**
     * This is the custom deserializer that deserializes a massage in a specific type of massage reading its type from the json
     * @param json is the json message to deserialize
     * @param type is the class type to deserialize
     * @param jsonDeserializationContext is the deserialization context
     * @return a message that extends MessageToClient
     * @throws JsonParseException the parsing goes wrong
     */
    @Override
    public MessageToClient deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Gson gson = new Gson();

        switch (jsonObject.get("messageType").getAsString()){
            case "MARKET_UPDATE":
                return gson.fromJson(jsonObject, MarketUpdate.class);
            case "CARD_GRID_UPDATE":
                return gson.fromJson(jsonObject, CardGridUpdate.class);
            case "INITIAL_LEADER_DISCARDED_UPDATE":
                return gson.fromJson(jsonObject, InitialLeaderDiscardedUpdate.class);
            case "PLAYERS_ORDER_UPDATE":
                return gson.fromJson(jsonObject, PlayersOrderUpdate.class);
            case "TEMPORARY_RESOURCE_MAP_UPDATE":
                return gson.fromJson(jsonObject, TemporaryResourceMapUpdate.class);
            case "TEMPORARY_MARBLES_UPDATE":
                return gson.fromJson(jsonObject, TemporaryMarblesUpdate.class);
            case "WAREHOUSE_UPDATE":
                return gson.fromJson(jsonObject, WarehouseUpdate.class);
            case "INITIAL_LEADERCARDS_UPDATE":
                return gson.fromJson(jsonObject, InitialLeaderCardsUpdate.class);
            case "PLAYER_TURN_UPDATE":
                return gson.fromJson(jsonObject, PlayerTurnUpdate.class);
            case "LEADERCARD_ACTIVATED_UPDATE":
                return gson.fromJson(jsonObject, LeaderCardActivatedUpdate.class);
            case "ADD_SPECIALDEPOT_UPDATE":
                return gson.fromJson(jsonObject, AddSpecialDepotUpdate.class);
            case "STRONGBOX_UPDATE":
                return gson.fromJson(jsonObject, StrongboxUpdate.class);
            case "REDCROSS_POSITION_UPDATE":
                return gson.fromJson(jsonObject, RedcrossPositionUpdate.class);
            case "RANK_UPDATE":
                return gson.fromJson(jsonObject, RankUpdate.class);
            case "POPE_FAVOUR_TILES_UPDATE":
                return gson.fromJson(jsonObject, PopeFavourTilesUpdate.class);
            case "FIRST_CONNECTION_UPDATE":
                return gson.fromJson(jsonObject, FirstConnectedUpdate.class);
            case "ERROR":
                return gson.fromJson(jsonObject, ErrorMessage.class);
            case "MAIN_TURN_ACTION_DONE_UPDATE":
                return gson.fromJson(jsonObject, MainTurnActionDoneUpdate.class);
            case "DISCARDED_LEADER_UPDATE":
                return gson.fromJson(jsonObject, DiscardedLeaderUpdate.class);
            case "DISCONNECTED_UPDATE":
                return gson.fromJson(jsonObject, DisconnectedUpdate.class);
        }
        return null;
    }

    /**
     *This method deserializes a string into a massage of a specific type that extends MessageToClient
     * @param serializedMessage is a String with the json massage to deserialize
     * @return a massage of a specific type that extends MessageToClient
     */
    public MessageToClient deserializeMessage(String serializedMessage){
        return customGson.fromJson(serializedMessage, MessageToClient.class);
    }
}
