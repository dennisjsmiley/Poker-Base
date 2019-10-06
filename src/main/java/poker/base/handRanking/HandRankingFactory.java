package poker.base.handRanking;

import poker.base.Card;
import poker.base.Hand;
import poker.base.enums.Rank;
import poker.base.enums.Suit;
import poker.base.exception.StraightFlushNotSameSuit;

import java.util.*;

public class HandRankingFactory {

    boolean isRoyalFlush(Hand hand) {
        if (hand.isSingleSuit()) {
            Map<Rank, Integer> rankCount = hand.getRankCount();

            Map<Rank, Integer> royalFlushRankCount = new HashMap<>();
            royalFlushRankCount.put(Rank.ACE, 1);
            royalFlushRankCount.put(Rank.KING, 1);
            royalFlushRankCount.put(Rank.QUEEN, 1);
            royalFlushRankCount.put(Rank.JACK, 1);
            royalFlushRankCount.put(Rank.TEN, 1);

            return royalFlushRankCount.equals(rankCount);
        }
        return false;
    }

    boolean isStraightFlush(Hand hand) {
        if (hand.isSingleSuit()) {
            Card lowHandCard = hand.getLowCard();
            Card highHandCard = hand.getHighCard();

            return !isRoyalFlush(hand) && lowHandCard.getRank().getRankIntegerValue() + 4 == highHandCard.getRank().getRankIntegerValue();
        }
        return false;
    }

    boolean isFourOfAKind(Hand hand) {
        Optional<Rank> fourOfAKindRank = hand.getFourOfAKindRank();
        return fourOfAKindRank.isPresent();
    }

    public HandRanking build(Hand hand) throws StraightFlushNotSameSuit {

        if (isRoyalFlush(hand)) {
            Optional<Suit> optionalSuit = hand.getSingleSuit();
            return new RoyalFlush(optionalSuit.get());
        }

        if (isStraightFlush(hand)) {
            return new StraightFlush(hand.getLowCard(), hand.getHighCard());
        }

        if (isFourOfAKind(hand)) {
            return new FourOfAKind(hand.getFourOfAKindRank().get());
        }

        // other hand rankings not yet handled
        return null;
    }

}
