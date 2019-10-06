package poker.base;

import poker.base.exception.NotAFullHandException;
import poker.base.exception.NullHandException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Hand {
    private final Set<Card> cards;

    public Hand(Set<Card> cards) throws NullHandException, NotAFullHandException {
        if (cards == null) {
            throw new NullHandException();
        }

        if (cards.size() != 5) {
            throw new NotAFullHandException();
        }

        this.cards = Collections.unmodifiableSet(cards);
    }

    public Set<Card> getCards() {
        return cards;
    }
}
