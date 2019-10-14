package poker.base.handRanking;

import poker.base.Hand;

public abstract class HandRanking implements Comparable<HandRanking> {
    private final Hand hand;

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

        int result = asEnum().compareTo(other.asEnum());
        if (result == 0) {
            // todo: handle tie breakers
        }
        return result; 
    }
}
