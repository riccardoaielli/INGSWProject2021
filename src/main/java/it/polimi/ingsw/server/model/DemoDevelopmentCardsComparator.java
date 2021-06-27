package it.polimi.ingsw.server.model;

import java.util.Comparator;

/**
 * Comparator used to compare development cards by ID
 */
public class DemoDevelopmentCardsComparator implements Comparator<DevelopmentCard> {
    /**
     * Override of compare to sort cards by ID
     * @param firstCard a development card
     * @param secondCard a development card
     * @return 0 if the first card's ID is greater than the second's card ID
     */
    @Override
    public int compare(DevelopmentCard firstCard, DevelopmentCard secondCard) {
        if(firstCard.getId() >= secondCard.getId())
            return 0;
        else
            return 1;
    }
}
