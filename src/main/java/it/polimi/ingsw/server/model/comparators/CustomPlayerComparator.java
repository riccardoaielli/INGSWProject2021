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
     * @return 0 if the player p1 has more victory points than the player p2 or if both have the same amount of victory points
     */
    @Override
    public int compare(Player p1, Player p2) {
        if(p1.getPersonalBoard().getVictoryPoints() >= p2.getPersonalBoard().getVictoryPoints())
            return 0;
        else
            return 1;
    }
}
