package poker.base.handRanking;

import poker.base.Hand;

public class HandRankingFactory {
    
    public HandRanking build(Hand hand) {

        HandRanking handRanking = null;

        if (hand.isRoyalFlush()) {
            handRanking = new RoyalFlush(hand);
        } else if (hand.isStraightFlush()) {
            handRanking = new StraightFlush(hand);
        } else if (hand.isFourOfAKind()) {
            handRanking = new FourOfAKind(hand);
        } else if (hand.isFullHouse()) {
            handRanking = new FullHouse(hand);
        } else if (hand.isFlush()) {
            handRanking = new Flush(hand);
        } else if (hand.isStraight()) {
            handRanking = new Straight(hand);
        } else if (hand.isThreeOfAKind()) {
            handRanking = new ThreeOfAKind(hand);
        }

        // other hand rankings not yet handled
        return handRanking;
    }

}
