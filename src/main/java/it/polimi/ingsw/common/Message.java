package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;

public abstract class Message {
    MessageType messageType;
    public abstract void handleMessage(Controller controller);
}
