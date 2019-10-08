package poker.base;

import poker.base.enums.Rank;
import poker.base.enums.Suit;

import java.util.AbstractMap;
import java.util.Map;

public class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Card) {
            Card other = (Card) object;
            return getSuit() == other.getSuit() && getRank() == other.getRank();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        Map.Entry<Suit, Rank> cardEntry = new AbstractMap.SimpleEntry<>(suit, rank);
        return cardEntry.hashCode();
    }

    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }
}
