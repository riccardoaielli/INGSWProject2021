package it.polimi.ingsw.model;

public interface Observable <T>{
    void addObserver(T observer);
}
