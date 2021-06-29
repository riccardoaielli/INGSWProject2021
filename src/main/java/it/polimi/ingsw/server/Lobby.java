package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.view.VirtualView;

import java.util.*;

/**
 * Class used to implement a lobby for creating multiple matches and managing players waiting for a player to choose the number of players
 */
public class Lobby {
    private Queue<VirtualView> waitingQueue;
    private Controller currSetupMatch;
    private int remainingFreeSlots;
    private Boolean demo;


    /**
     * Constructor of lobby class
     * @param demo True if the server is in demo mode and every match will be created in demo mode
     */
    public Lobby(Boolean demo) {
        this.demo = demo;
        remainingFreeSlots = 0;
        currSetupMatch = new Controller(demo, this);
        waitingQueue = new LinkedList<>();
    }

    /**
     * Method used to get the controller of an available match or add a client to the waiting queue if a player is choosing the number of players
     * @param newClient Client that requests to play
     * @return The controller of an available match, if there is one otherwise the method adds the client to a waiting queue and returns null
     */
    public synchronized Controller getAvailableMatch(VirtualView newClient){
        //Checking if the match that is being created has already a first player connected
        if(currSetupMatch.hasFirstConnected()){
            //Checking if the first player connected has already chosen the number of players
            if(currSetupMatch.isNumOfPlayerChosen()){
                //Checking if there is at least a free slot available in the match
                if (remainingFreeSlots >0) {
                    remainingFreeSlots--;
                }
                //else a new match is created
                else{
                    currSetupMatch = new Controller(demo, this);
                }
                return currSetupMatch;
            }
            //If the first players has not chosen the number of players yet, the client is added to a waiting queue
            else{
                waitingQueue.add(newClient);
                return null;
            }
        }
        else{
            return currSetupMatch;
        }
    }

    /**
     * Method used to add the waiting client to a match after a player has chosen the number of players.
     * If there are more clients waiting than the maximum number of players allowed in the match, a new match is created and the first player in queue gets to choose the number of players of the new match
     * @param numOfPlayers The number of players chosen by the first player who entered the match
     */
    public synchronized void notifyNumOfPlayers(int numOfPlayers){
        //Subtracting the player who has chosen the number of players
        remainingFreeSlots = numOfPlayers-1;
        //The minimum between clients waiting and number of players to add
        int numOfPlayersToAdd = Math.min(remainingFreeSlots, waitingQueue.size());

        for(int i = 1; i<= numOfPlayersToAdd; i++){
            VirtualView virtualViewToAdd = waitingQueue.remove();
            virtualViewToAdd.setController(currSetupMatch);
            remainingFreeSlots--;
        }
        if(!waitingQueue.isEmpty()){
            //Adding the first excluded player of the match that is now full to a new match
            currSetupMatch = new Controller(demo, this);
            VirtualView virtualViewToAdd = waitingQueue.remove();
            virtualViewToAdd.setController(currSetupMatch);
            remainingFreeSlots--;
        }
    }
}
