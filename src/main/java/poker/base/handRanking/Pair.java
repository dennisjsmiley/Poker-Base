package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

import java.util.Optional;

public class Pair extends HandRanking {

    public Pair(Hand hand) {
        super(hand);
    }

    public Optional<Rank> getRank() {
        return Optional.of(getHand().getTwoOfAKindRank().get(0));
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.PAIR;
    }

    @Override
    public String toString() {
        return asEnum().toString() + " (" + getRank().toString() + ")";
    }
}
