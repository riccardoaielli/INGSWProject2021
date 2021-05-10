package it.polimi.ingsw.common;

public abstract class Message {
    MessageType messageType;
    public abstract void handleMessage();
}
