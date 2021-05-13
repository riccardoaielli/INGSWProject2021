package it.polimi.ingsw.common;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.HashMap;

public class TransformMarblesMessage extends MessageToServer{
    private int depotLevel;
    private HashMap<Resource,Integer> singleResourceMap;

    public TransformMarblesMessage(String nickname, MessageType messageType, int depotLevel, HashMap<Resource,Integer> singleResourceMap) {
        super(nickname, messageType);
        this.depotLevel = depotLevel;
        this.singleResourceMap = singleResourceMap;
    }

    @Override
    public void handleMessage(Controller controller, View virtualView) { controller.handleTransformMarblesMessage(depotLevel, singleResourceMap, this.getNickname()); }
}
