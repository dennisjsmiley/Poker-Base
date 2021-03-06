package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

import java.util.Optional;

public class ThreeOfAKind extends HandRanking {

    public ThreeOfAKind(Hand hand) {
        super(hand);
    }

    public Optional<Rank> getRank() {
        return getHand().getThreeOfAKindRank();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.THREE_OF_A_KIND;
    }

    @Override
    public String toString() {
        return asEnum().toString() + " (" + getRank().toString() + ")";
    }
}
