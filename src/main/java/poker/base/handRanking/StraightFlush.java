package poker.base.handRanking;

import poker.base.Card;
import poker.base.Hand;

public class StraightFlush extends HandRanking {

    public StraightFlush(Hand hand) {
        super(hand);
    }

    public Card getLowCard() {
        return hand.getLowCard();
    }

    public Card getHighCard() {
        return hand.getHighCard();
    }
}
