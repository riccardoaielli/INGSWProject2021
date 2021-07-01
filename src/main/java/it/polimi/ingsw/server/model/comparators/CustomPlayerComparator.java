package it.polimi.ingsw.server.model.comparators;

import it.polimi.ingsw.server.model.Player;

import java.util.Comparator;

/**
 * Comparator used to compare two player by their victory points
 */
public class CustomPlayerComparator implements Comparator<Player> {

    /**
     * Override of compare to sort players by victory points
     * @param p1 a player
     * @param p2 a player
     * @return a compare value between the victory points of the players
     */
    @Override
    public int compare(Player p1, Player p2) {
        return Integer.compare(p1.getPersonalBoard().getVictoryPoints(), p2.getPersonalBoard().getVictoryPoints());
    }
}
