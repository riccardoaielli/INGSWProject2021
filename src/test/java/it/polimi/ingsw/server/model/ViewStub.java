package it.polimi.ingsw.server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.common.View;
import it.polimi.ingsw.common.messages.messagesToClient.MessageToClient;

public class ViewStub implements View {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public void update(MessageToClient message) {
        System.out.println(gson.toJson(message));
    }
}
