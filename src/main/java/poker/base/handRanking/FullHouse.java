package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

public class FullHouse extends HandRanking {

    public FullHouse(Hand hand) {
        super(hand);
    }

    public Rank getThreeOfAKindRank() {
        return hand.getThreeOfAKindRank().get();
    }

    public Rank getTwoOfAKindRank() {
        return hand.getTwoOfAKindRank().get();
    }
}
