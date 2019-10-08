package poker.base.handRanking;

import poker.base.enums.Suit;

public class RoyalFlush extends HandRanking {

    private final Suit suit;

    public RoyalFlush(Suit suit) {
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }
}
