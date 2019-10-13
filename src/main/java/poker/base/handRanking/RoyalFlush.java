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
}
