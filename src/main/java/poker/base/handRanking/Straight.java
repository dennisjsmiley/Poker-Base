package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

import java.util.Optional;

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
    public Optional<Rank> getRank() {
        return Optional.empty();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.STRAIGHT;
    }

    @Override
    public String toString() {
        return asEnum().toString() + " (" + getHighRank().toString() + ", " + getLowRank().toString() + ")";
    }
}
