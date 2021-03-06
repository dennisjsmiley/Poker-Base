package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

import java.util.*;

public class TwoPair extends HandRanking {

    public TwoPair(Hand hand) {
        super(hand);
    }

    public Set<Rank> getRanks() {
        Set<Rank> ranks = new HashSet<>();
        ranks.addAll(getHand().getTwoOfAKindRank());
        return Collections.unmodifiableSet(ranks);
    }

    public List<Rank> getRanksSorted() {
        List<Rank> ranks = getHand().getTwoOfAKindRank();
        ranks.sort((rank1, rank2) -> rank2.compareTo(rank1));
        return Collections.unmodifiableList(ranks);
    }

    @Override
    public Optional<Rank> getRank() {
        return Optional.empty();
    }

    @Override
    public poker.base.enums.HandRanking asEnum() {
        return poker.base.enums.HandRanking.TWO_PAIR;
    }

    @Override
    public String toString() {
        return asEnum().toString() + " (" + getRanks().toString() + ")";
    }
}
