package poker.base.handRanking;

import poker.base.Card;
import poker.base.Hand;
import poker.base.enums.Rank;
import poker.base.enums.Suit;
import poker.base.exception.StraightFlushNotSameSuit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HandRankingFactory {

    public HandRanking build(Hand hand) throws StraightFlushNotSameSuit {
        Set<Suit> handSuits = new HashSet<>();
        Map<Rank, Integer> rankCount = new HashMap<>();

        for (Card card : hand.getCards()) {
            handSuits.add(card.getSuit());

            if (rankCount.containsKey(card.getRank())) {
                rankCount.put(card.getRank(), 1 + rankCount.get(card.getRank()));
            } else {
                rankCount.put(card.getRank(), 1);
            }
        }

        if (handSuits.size() == 1) {
            Suit handSuit = null;
            for (Suit suit : handSuits) {
                handSuit = suit;
            }

            // check for royal flush
            Map<Rank, Integer> royalFlushRankCount = new HashMap<>();
            royalFlushRankCount.put(Rank.ACE, 1);
            royalFlushRankCount.put(Rank.KING, 1);
            royalFlushRankCount.put(Rank.QUEEN, 1);
            royalFlushRankCount.put(Rank.JACK, 1);
            royalFlushRankCount.put(Rank.TEN, 1);

            if (royalFlushRankCount.equals(rankCount)) {
                return new RoyalFlush(handSuit);
            }

            // check for straight flush
            Card minHandCard = null;
            Card maxHandCard = null;

            for (Card card : hand.getCards()) {

                if (minHandCard == null) {
                    minHandCard = card;
                } else {
                    if (card.getRank().getRankIntegerValue() < minHandCard.getRank().getRankIntegerValue()) {
                        minHandCard = card;
                    }
                }

                if (maxHandCard == null) {
                    maxHandCard = card;
                } else {
                    if (card.getRank().getRankIntegerValue() > maxHandCard.getRank().getRankIntegerValue()) {
                        maxHandCard = card;
                    }
                }

            }

            if (minHandCard.getRank().getRankIntegerValue() + 4 == maxHandCard.getRank().getRankIntegerValue()) {
                return new StraightFlush(minHandCard, maxHandCard);
            }
        }

        // other hand rankings not yet handled
        return null;
    }

}
