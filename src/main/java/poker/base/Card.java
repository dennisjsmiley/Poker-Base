package poker.base;

import poker.base.enums.Rank;
import poker.base.enums.Suit;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

public class Card implements Comparable<Card> {
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

    public static Optional<Card> fromShortCode(String string) {
        string = string.toLowerCase();
        String rankCode = string.substring(0, string.length() - 1);
        String suitCode = string.substring(string.length() - 1);

        Optional<Rank> optionalRank = Rank.fromShortCode(rankCode);
        Optional<Suit> optionalSuit = Suit.fromShortCode(suitCode);

        if (optionalRank.isPresent() && optionalSuit.isPresent()) {
            return Optional.of(new Card(optionalSuit.get(), optionalRank.get()));
        } else {
            return Optional.empty();
        }
    }

    public String toShortCode() {
        return getRank().getShortCode() + getSuit().getShortCode();
    }

    @Override
    public int compareTo(Card other) {
        if (other == null) {
            throw new NullPointerException();
        }
        return getRank().compareTo(other.getRank());
    }
}
