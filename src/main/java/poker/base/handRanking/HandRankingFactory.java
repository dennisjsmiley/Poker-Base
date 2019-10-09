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

        // other hand rankings not yet handled
        return handRanking;
    }

}
