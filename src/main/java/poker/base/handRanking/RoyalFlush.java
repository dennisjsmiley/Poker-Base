package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Suit;

public class RoyalFlush extends HandRanking {

    public RoyalFlush(Hand hand) {
        super(hand);
    }

    public Suit getSuit() {
        return hand.getSingleSuit().get();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.ROYAL_FLUSH;
    }
}
