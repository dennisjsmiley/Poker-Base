package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Suit;

public class Flush extends HandRanking {

    public Flush(Hand hand) {
        super(hand);
    }

    public Suit getSuit() {
        return getHand().getSingleSuit().get();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.FLUSH;
    }
}
