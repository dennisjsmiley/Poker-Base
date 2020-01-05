package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;
import poker.base.enums.Suit;

import java.util.Optional;

public class Flush extends HandRanking {

    public Flush(Hand hand) {
        super(hand);
    }

    public Suit getSuit() {
        return getHand().getSingleSuit().get();
    }

    @Override
    public Optional<Rank> getRank() {
        return Optional.empty();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.FLUSH;
    }

    @Override
    public String toString() {
        return asEnum().toString() + " (" + getSuit().toString() + ")";
    }
}
