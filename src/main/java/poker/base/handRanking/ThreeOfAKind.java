package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

public class ThreeOfAKind extends HandRanking {

    public ThreeOfAKind(Hand hand) {
        super(hand);
    }

    public Rank getRank() {
        return hand.getThreeOfAKindRank().get();
    }
}
