package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

import java.util.Optional;

public class FourOfAKind extends HandRanking {

    public FourOfAKind(Hand hand) {
        super(hand);
    }

    public Optional<Rank> getRank() {
        return getHand().getFourOfAKindRank();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.FOUR_OF_A_KIND;
    }

    @Override
    public String toString() {
        return asEnum().toString() + " (" + getRank().toString() + ")";
    }
}
