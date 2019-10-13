package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

public class FourOfAKind extends HandRanking {

    public FourOfAKind(Hand hand) {
        super(hand);
    }

    public Rank getRank() {
        return getHand().getFourOfAKindRank().get();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.FOUR_OF_A_KIND;
    }
}
