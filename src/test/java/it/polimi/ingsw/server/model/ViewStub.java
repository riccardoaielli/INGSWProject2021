package it.polimi.ingsw.server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.common.Message;
import it.polimi.ingsw.common.View;

public class ViewStub implements View {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public void update(Message message) {
        System.out.println(gson.toJson(message));
    }
}
