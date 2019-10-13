package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

public class Straight extends HandRanking {

    public Straight(Hand hand) {
        super(hand);
    }

    public Rank getLowRank() {
        return hand.getLowCard().getRank();
    }

    public Rank getHighRank() {
        return hand.getHighCard().getRank();
    }
}
