package poker.base.handRanking;

import poker.base.Card;
import poker.base.Hand;
import poker.base.enums.Rank;

import java.util.Optional;

public class StraightFlush extends HandRanking {

    public StraightFlush(Hand hand) {
        super(hand);
    }

    public Card getLowCard() {
        return getHand().getLowCard();
    }

    public Card getHighCard() {
        return getHand().getHighCard();
    }

    @Override
    public Optional<Rank> getRank() {
        return Optional.empty();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.STRAIGHT_FLUSH;
    }

    @Override
    public String toString() {
        return asEnum().toString() + " (" + getHighCard().toString() + ", " + getLowCard().toString() + ")";
    }
}
