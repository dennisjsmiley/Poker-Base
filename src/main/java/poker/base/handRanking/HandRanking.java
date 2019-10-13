package poker.base.handRanking;

import poker.base.Hand;

public abstract class HandRanking {
    protected final Hand hand;

    public HandRanking(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }
}
