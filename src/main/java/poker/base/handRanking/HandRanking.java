package poker.base.handRanking;

import poker.base.Hand;

public abstract class HandRanking implements Comparable<HandRanking> {
    protected final Hand hand;

    public HandRanking(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    public abstract poker.base.enums.HandRanking asEnum();

    @Override
    public int compareTo(HandRanking other) {
        if (other == null) {
            throw new NullPointerException();
        }

        if (asEnum().toInteger() > other.asEnum().toInteger()) {
            return 1;
        } else if (asEnum().toInteger() < other.asEnum().toInteger()) {
            return -1;
        } else {
            // todo: handle tie breaker cases
            return 0;
        }
    }
}
