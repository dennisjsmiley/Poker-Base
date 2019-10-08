package poker.base.handRanking;

import poker.base.enums.Rank;

public class FourOfAKind extends HandRanking {

    private final Rank rank;

    public FourOfAKind(Rank rank) {
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }
}