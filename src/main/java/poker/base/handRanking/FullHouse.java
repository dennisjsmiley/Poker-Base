package poker.base.handRanking;

import poker.base.enums.Rank;

public class FullHouse extends HandRanking {
    private final Rank threeOfAKindRank;
    private final Rank twoOfAKindRank;

    public FullHouse(Rank threeOfAKindRank, Rank twoOfAKindRank) {
        this.threeOfAKindRank = threeOfAKindRank;
        this.twoOfAKindRank = twoOfAKindRank;
    }

    public Rank getThreeOfAKindRank() {
        return threeOfAKindRank;
    }

    public Rank getTwoOfAKindRank() {
        return twoOfAKindRank;
    }
}
