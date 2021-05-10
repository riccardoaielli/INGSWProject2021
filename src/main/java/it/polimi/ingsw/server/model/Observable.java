package it.polimi.ingsw.server.model;

public interface Observable <T>{
    void addObserver(T observer);
}
