package poker.base.handRanking;

import poker.base.enums.Suit;

public class Flush extends HandRanking {
    private final Suit suit;

    public Flush(Suit suit) {
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }
}
