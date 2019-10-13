package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

public class Straight extends HandRanking {

    public Straight(Hand hand) {
        super(hand);
    }

    public Rank getLowRank() {
        return getHand().getLowCard().getRank();
    }

    public Rank getHighRank() {
        return getHand().getHighCard().getRank();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.STRAIGHT;
    }
}
