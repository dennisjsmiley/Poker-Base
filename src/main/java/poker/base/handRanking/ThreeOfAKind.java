package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

public class ThreeOfAKind extends HandRanking {

    public ThreeOfAKind(Hand hand) {
        super(hand);
    }

    public Rank getRank() {
        return getHand().getThreeOfAKindRank().get();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.THREE_OF_A_KIND;
    }
}
