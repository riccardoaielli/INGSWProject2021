package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.GUI;

public abstract class AbstractController {
    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public GUI getGui() {
        return gui;
    }
}
