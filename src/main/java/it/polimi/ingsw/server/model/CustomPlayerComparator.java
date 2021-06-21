package it.polimi.ingsw.server.model;

import java.util.Comparator;

/**
 * Comparator used to compare two player by their victory points
 */
public class CustomPlayerComparator implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        //sarebbe meglio mettere un getter nella personal board per i vp al posto di utilizzare sumVictoryPoints
        if(o1.getPersonalBoard().getVictoryPoints() >= o2.getPersonalBoard().getVictoryPoints())
            return 0;
        else
            return 1;
    }
}
