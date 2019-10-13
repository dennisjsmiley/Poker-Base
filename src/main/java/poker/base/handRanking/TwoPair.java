package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TwoPair extends HandRanking {

    public TwoPair(Hand hand) {
        super(hand);
    }

    public Set<Rank> getRanks() {
        Set<Rank> ranks = new HashSet<>();
        ranks.addAll(getHand().getTwoOfAKindRank());
        return Collections.unmodifiableSet(ranks);
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.TWO_PAIR;
    }
}
