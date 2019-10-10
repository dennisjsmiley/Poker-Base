package poker.base.handRanking;

import poker.base.Card;
import poker.base.Hand;
import poker.base.enums.Rank;
import poker.base.enums.Suit;
import poker.base.exception.StraightFlushNotSameSuit;

import java.util.*;

public class HandRankingFactory {



    public HandRanking build(Hand hand) throws StraightFlushNotSameSuit {

        HandRanking handRanking = null;

        if (hand.isRoyalFlush()) {
            Optional<Suit> optionalSuit = hand.getSingleSuit();
            handRanking = new RoyalFlush(optionalSuit.get());
        }

        if (hand.isStraightFlush()) {
            handRanking = new StraightFlush(hand.getLowCard(), hand.getHighCard());
        }

        if (hand.isFourOfAKind()) {
            handRanking = new FourOfAKind(hand.getFourOfAKindRank().get());
        }

        if (hand.isFullHouse()) {
            handRanking = new FullHouse(hand.getThreeOfAKindRank().get(), hand.getTwoOfAKindRank().get());
        }

        if (hand.isFlush()) {
            handRanking = new Flush(hand.getSingleSuit().get());
        }

        if (hand.isStraight()) {
            handRanking = new Straight(hand.getLowCard().getRank(), hand.getHighCard().getRank());
        }

        // other hand rankings not yet handled
        return handRanking;
    }

}
