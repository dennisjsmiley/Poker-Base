package poker.base.handRanking;

import poker.base.Hand;
import poker.base.enums.Rank;

public class HighCard extends HandRanking {

    public HighCard(Hand hand) {
        super(hand);
    }

    public Rank getRank() {
        return hand.getHighCard().getRank();
    }
}
