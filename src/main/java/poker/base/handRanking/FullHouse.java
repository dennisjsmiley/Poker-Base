package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

public class FullHouse extends HandRanking {

    public FullHouse(Hand hand) {
        super(hand);
    }

    public Rank getThreeOfAKindRank() {
        return getHand().getThreeOfAKindRank().get();
    }

    public Rank getTwoOfAKindRank() {
        return getHand().getTwoOfAKindRank().get(0);
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.FULL_HOUSE;
    }

    @Override
    public String toString() {
        return asEnum().toString() + " (3x " + getThreeOfAKindRank().toString() + ", 2x " + getTwoOfAKindRank().toString() + ")";
    }
}
