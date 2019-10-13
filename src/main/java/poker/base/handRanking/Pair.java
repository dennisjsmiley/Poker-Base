package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

public class Pair extends HandRanking {

    public Pair(Hand hand) {
        super(hand);
    }

    public Rank getRank() {
        return hand.getTwoOfAKindRank().get(0);
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.PAIR;
    }
}
