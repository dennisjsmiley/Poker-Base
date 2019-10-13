package poker.base;

import poker.base.enums.Rank;
import poker.base.enums.Suit;
import poker.base.exception.NullHandException;

import java.util.*;

public class Hand {
    private final Set<Card> cards;

    public Hand(Set<Card> cards) {
        if (cards == null) {
            cards = new HashSet<>();
        }

        this.cards = Collections.unmodifiableSet(cards);
    }

    public Set<Card> getCards() {
        return cards;
    }

    public Card getLowCard() {
        Card lowCard = null;
        for (Card card : getCards()) {
            if (lowCard == null) {
                lowCard = card;
            } else {
                if (card.getRank().toInteger() < lowCard.getRank().toInteger()) {
                    lowCard = card;
                }
            }
        }
        return lowCard;
    }

    public Card getHighCard() {
        Card highCard = null;
        for (Card card : getCards()) {
            if (highCard == null) {
                highCard = card;
            } else {
                if (card.getRank().toInteger() > highCard.getRank().toInteger()) {
                    highCard = card;
                }
            }
        }
        return highCard;
    }

    public Set<Suit> getSuits() {
        Set<Suit> handSuits = new HashSet<>();
        for (Card card : getCards()) {
            handSuits.add(card.getSuit());
        }
        return handSuits;
    }

    public Map<Rank, Integer> getRankCount() {
        Map<Rank, Integer> rankCount = new HashMap<>();
        for (Card card : getCards()) {
            if (rankCount.containsKey(card.getRank())) {
                rankCount.put(card.getRank(), 1 + rankCount.get(card.getRank()));
            } else {
                rankCount.put(card.getRank(), 1);
            }
        }
        return rankCount;
    }

    public boolean isSingleSuit() {
        Set<Suit> handSuits = getSuits();
        return handSuits.size() == 1;
    }

    public Optional<Suit> getSingleSuit() {
        if (isSingleSuit()) {
            for (Suit suit : getSuits()) {
                return Optional.of(suit);
            }
        }
        return Optional.empty();
    }

    private Optional<Rank> getXOfAKindRank(int x) {
        for (Map.Entry<Rank, Integer> rankCountEntry : getRankCount().entrySet()) {
            if (rankCountEntry.getValue() == x) {
                return Optional.of(rankCountEntry.getKey());
            }
        }
        return Optional.empty();
    }

    public Optional<Rank> getFourOfAKindRank() {
        return getXOfAKindRank(4);
    }

    public Optional<Rank> getThreeOfAKindRank() {
        return getXOfAKindRank(3);
    }

    public Optional<Rank> getTwoOfAKindRank() {
        return getXOfAKindRank(2);
    }

    public boolean isFullHand() {
        return getCards().size() == 5;
    }

    // Hand ranking tests

    public boolean isRoyalFlush() {
        if (!isFullHand()) {
            return false;
        }

        if (isSingleSuit()) {
            Map<Rank, Integer> rankCount = getRankCount();

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

    public boolean isStraightFlush() {
        if (!isFullHand()) {
            return false;
        }

        if (isSingleSuit()) {
            Card lowHandCard = getLowCard();
            Card highHandCard =getHighCard();

            return !isRoyalFlush() && lowHandCard.getRank().toInteger() + 4 == highHandCard.getRank().toInteger();
        }
        return false;
    }

    public boolean isFourOfAKind() {
        if (!isFullHand()) {
            return false;
        }

        Optional<Rank> fourOfAKindRank = getFourOfAKindRank();
        return fourOfAKindRank.isPresent();
    }

    public boolean isFullHouse() {
        if (!isFullHand()) {
            return false;
        }

        Optional<Rank> threeOfAKindRank = getThreeOfAKindRank();
        Optional<Rank> twoOfAKindRank = getTwoOfAKindRank();
        return threeOfAKindRank.isPresent() && twoOfAKindRank.isPresent();
    }

    public boolean isFlush() {
        if (!isFullHand()) {
            return false;
        }

        return isSingleSuit() && getLowCard().getRank().toInteger() + 4 != getHighCard().getRank().toInteger();
    }

    public boolean isStraight() {
        if (!isFullHand()) {
            return false;
        }

        return !isSingleSuit() && getLowCard().getRank().toInteger() + 4 == getHighCard().getRank().toInteger();
    }

    public boolean isThreeOfAKind() {
        if (!isFullHand()) {
            return false;
        }

        return getThreeOfAKindRank().isPresent() && !getTwoOfAKindRank().isPresent();
    }
}
