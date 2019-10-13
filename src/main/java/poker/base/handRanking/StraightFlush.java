package poker.base.handRanking;

import poker.base.Card;
import poker.base.Hand;

public class StraightFlush extends HandRanking {

    public StraightFlush(Hand hand) {
        super(hand);
    }

    public Card getLowCard() {
        return getHand().getLowCard();
    }

    public Card getHighCard() {
        return getHand().getHighCard();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.STRAIGHT_FLUSH;
    }

    @Override
    public String toString() {
        return asEnum().toString() + " (" + getHighCard().toString() + ", " + getLowCard().toString() + ")";
    }
}
