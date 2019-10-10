package poker.base.handRanking;

import poker.base.enums.Rank;

public class Straight extends HandRanking {
    private final Rank lowRank;
    private final Rank highRank;

    public Straight(Rank lowRank, Rank highRank) {
        this.lowRank = lowRank;
        this.highRank = highRank;
    }

    public Rank getLowRank() {
        return lowRank;
    }

    public Rank getHighRank() {
        return highRank;
    }
}
