package it.polimi.ingsw.server.model.comparators;

import it.polimi.ingsw.server.model.DevelopmentCard;

import java.util.Comparator;

/**
 * Comparator used to compare development cards by ID
 */
public class DemoDevelopmentCardsComparator implements Comparator<DevelopmentCard> {
    /**
     * Override of compare to sort cards by ID
     * @param firstCard a development card
     * @param secondCard a development card
     * @return a compare value between the id of the cards
     */
    @Override
    public int compare(DevelopmentCard firstCard, DevelopmentCard secondCard) {
        return Integer.compare(firstCard.getId(), secondCard.getId());
    }
}
