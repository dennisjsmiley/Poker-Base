package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

public class FourOfAKind extends HandRanking {

    public FourOfAKind(Hand hand) {
        super(hand);
    }

    public Rank getRank() {
        return hand.getFourOfAKindRank().get();
    }
}
