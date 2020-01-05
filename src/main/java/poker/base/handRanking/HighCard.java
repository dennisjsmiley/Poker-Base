package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

import java.util.Optional;

public class HighCard extends HandRanking {

    public HighCard(Hand hand) {
        super(hand);
    }

    public Optional<Rank> getRank() {
        return Optional.of(getHand().getHighCard().getRank());
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.HIGH_CARD;
    }

    @Override
    public String toString() {
        return asEnum().toString() + " (" + getRank().toString() + ")";
    }
}
