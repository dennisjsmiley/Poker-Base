package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Suit;

public class Flush extends HandRanking {

    public Flush(Hand hand) {
        super(hand);
    }

    public Suit getSuit() {
        return hand.getSingleSuit().get();
    }
}
