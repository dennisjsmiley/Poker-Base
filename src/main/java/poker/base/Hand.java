package poker.base;

import poker.base.enums.Rank;
import poker.base.enums.Suit;
import poker.base.exception.NotAFullHandException;
import poker.base.exception.NullHandException;

import java.util.*;

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

    public Card getLowCard() {
        Card lowCard = null;
        for (Card card : getCards()) {
            if (lowCard == null) {
                lowCard = card;
            } else {
                if (card.getRank().getRankIntegerValue() < lowCard.getRank().getRankIntegerValue()) {
                    lowCard = card;
                }
            }
        }
        return lowCard;
    }

    public Card getHighCard() {
        Card highCard = null;
        for (Card card : getCards()) {
            if (highCard == null) {
                highCard = card;
            } else {
                if (card.getRank().getRankIntegerValue() > highCard.getRank().getRankIntegerValue()) {
                    highCard = card;
                }
            }
        }
        return highCard;
    }

    public Set<Suit> getSuits() {
        Set<Suit> handSuits = new HashSet<>();
        for (Card card : getCards()) {
            handSuits.add(card.getSuit());
        }
        return handSuits;
    }

    public Map<Rank, Integer> getRankCount() {
        Map<Rank, Integer> rankCount = new HashMap<>();
        for (Card card : getCards()) {
            if (rankCount.containsKey(card.getRank())) {
                rankCount.put(card.getRank(), 1 + rankCount.get(card.getRank()));
            } else {
                rankCount.put(card.getRank(), 1);
            }
        }
        return rankCount;
    }

    public boolean isSingleSuit() {
        Set<Suit> handSuits = getSuits();
        return handSuits.size() == 1;
    }

    public Optional<Suit> getSingleSuit() {
        if (isSingleSuit()) {
            for (Suit suit : getSuits()) {
                return Optional.of(suit);
            }
        }
        return Optional.empty();
    }
}
