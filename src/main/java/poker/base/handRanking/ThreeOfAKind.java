package poker.base.handRanking;

import poker.base.enums.Rank;

public class ThreeOfAKind extends HandRanking {
    private final Rank rank;

    public ThreeOfAKind(Rank rank) {
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }
}
