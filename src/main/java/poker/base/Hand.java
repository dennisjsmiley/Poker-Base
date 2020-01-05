package poker.base;

import poker.base.enums.Rank;
import poker.base.enums.Suit;
import poker.base.handRanking.*;
import poker.base.util.PokerUtil;

import java.util.*;

public class Hand implements Comparable<Hand> {
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

    public List<Card> getCardsSorted() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(getCards());
        cards.sort((card1, card2) -> (card2.compareTo(card1)));
        return Collections.unmodifiableList(cards);
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
        return Collections.unmodifiableSet(handSuits);
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
        return Collections.unmodifiableMap(rankCount);
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

    public List<Rank> getXOfAKindRank(int x) {
        List<Rank> ranks = new ArrayList<>();
        getRankCount().forEach((rank, count) -> {
            if (count == x) {
                ranks.add(rank);
            }
        });
        return ranks;
    }

    public Optional<Rank> getFourOfAKindRank() {
        List<Rank> ranks = getXOfAKindRank(4);
        return ranks.isEmpty() ? Optional.empty() : Optional.of(ranks.get(0));
    }

    public Optional<Rank> getThreeOfAKindRank() {
        List<Rank> ranks = getXOfAKindRank(3);
        return ranks.isEmpty() ? Optional.empty() : Optional.of(ranks.get(0));
    }

    public List<Rank> getTwoOfAKindRank() {
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
        return isFullHand() && getFourOfAKindRank().isPresent();
    }

    public boolean isFullHouse() {
        return isFullHand() && getThreeOfAKindRank().isPresent() && getTwoOfAKindRank().size() == 1;
    }

    public boolean isFlush() {
        return isFullHand() && isSingleSuit() && getLowCard().getRank().toInteger() + 4 != getHighCard().getRank().toInteger();
    }

    public boolean isStraight() {
        boolean isFullHandAndNotSingleSuit = isFullHand() && !isSingleSuit();

        boolean isAllCardsInOrderByRank = true;
        int highRankInt = getHighCard().getRank().toInteger();
        List<Card> cardsSorted = getCardsSorted();
        for (int i = 0; i < cards.size(); i++) {
            isAllCardsInOrderByRank = isAllCardsInOrderByRank && ((highRankInt - i) == cardsSorted.get(i).getRank().toInteger());
            if (!isAllCardsInOrderByRank) {
                break;
            }
        }

        return isFullHandAndNotSingleSuit && isAllCardsInOrderByRank;
    }

    public boolean isThreeOfAKind() {
        return isFullHand() && getThreeOfAKindRank().isPresent() && getTwoOfAKindRank().isEmpty();
    }

    public boolean isTwoPair() {
        return isFullHand() && getTwoOfAKindRank().size() == 2;
    }

    public boolean isPair() {
        return isFullHand() && getTwoOfAKindRank().size() == 1 && !getThreeOfAKindRank().isPresent();
    }

    public HandRanking getHandRanking() {
        HandRanking handRanking = null;

        if (isRoyalFlush()) {
            handRanking = new RoyalFlush(this);
        } else if (isStraightFlush()) {
            handRanking = new StraightFlush(this);
        } else if (isFourOfAKind()) {
            handRanking = new FourOfAKind(this);
        } else if (isFullHouse()) {
            handRanking = new FullHouse(this);
        } else if (isFlush()) {
            handRanking = new Flush(this);
        } else if (isStraight()) {
            handRanking = new Straight(this);
        } else if (isThreeOfAKind()) {
            handRanking = new ThreeOfAKind(this);
        } else if (isTwoPair()) {
            handRanking = new TwoPair(this);
        } else if (isPair()) {
            handRanking = new Pair(this);
        } else {
            handRanking = new HighCard(this);
        }

        return handRanking;
    }

    public static Hand fromShortCodes(String string) {
        Set<Card> cards = new HashSet<>();

        String[] parts = string.split(",");
        for (String part : parts) {
            Optional<Card> optionalCard = Card.fromShortCode(part.trim());
            optionalCard.ifPresent(cards::add);
        }

        return new Hand(cards);
    }

    public String toShortCode() {
        List<String> parts = new ArrayList<>();
        for (Card card : getCardsSorted()) {
            parts.add(card.toShortCode());
        }
        return String.join(",", parts);
    }

    @Override
    public String toString() {
        return toShortCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (other instanceof Hand) {
            return cards.equals(((Hand) other).getCards());
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Hand other) {
        return getHandRanking().compareTo(other.getHandRanking());
    }

    public static List<Hand> getHands(Set<Card> holeCards, List<Card> communityCards) {
        List<Hand> hands = new ArrayList<>();
        Set<Set<Card>> threeCardCombos = PokerUtil.computeThreeCardCombinations(communityCards);
        for (Set<Card> threeCardCombo : threeCardCombos) {
            Set<Card> cards = new HashSet<>();
            cards.addAll(holeCards);
            cards.addAll(threeCardCombo);

            Hand hand = new Hand(cards);
            hands.add(hand);
        }
        Collections.sort(hands, Collections.reverseOrder());
        return hands;
    }
}
