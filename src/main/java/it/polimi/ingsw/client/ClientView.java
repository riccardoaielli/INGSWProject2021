package it.polimi.ingsw.client;

import it.polimi.ingsw.common.View;

/**
 * Interface implemented by GUI and CLI containing methods used to display changes and messages from controller and model
 */
public interface ClientView extends View {
    void showError(String errorString);
}
