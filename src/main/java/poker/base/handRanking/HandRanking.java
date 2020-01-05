package poker.base.handRanking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.Card;
import poker.base.Hand;
import poker.base.enums.Rank;
import poker.base.util.PokerUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class HandRanking implements Comparable<HandRanking> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Hand hand;

    public HandRanking(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    public abstract poker.base.enums.HandRanking asEnum();

    public abstract Optional<Rank> getRank();

    @Override
    public int compareTo(HandRanking other) {
        if (other == null) {
            throw new NullPointerException();
        }

        int result = asEnum().compareTo(other.asEnum());
        if (result == 0) {
            result = handleHandRankingTie(this, other);
        }
        return result; 
    }

    int handleHandRankingTie(HandRanking self, HandRanking other) {
        int result = 0;
        switch (self.asEnum()) {
            case ROYAL_FLUSH:
                result = handleRoyalFlushTie((RoyalFlush) self, (RoyalFlush) other);
                break;
            case STRAIGHT_FLUSH:
                result = handleStraightFlushTie((StraightFlush) self, (StraightFlush) other);
                break;
            case FOUR_OF_A_KIND:
                result = handleFourOfAKindTie((FourOfAKind) self, (FourOfAKind) other);
                break;
            case FULL_HOUSE:
                result = handleFullHouseTie((FullHouse) self, (FullHouse) other);
                break;
            case FLUSH:
                result = handleFlushTie((Flush) self, (Flush) other);
                break;
            case STRAIGHT:
                result = handleStraightTie((Straight) self, (Straight) other);
                break;
            case THREE_OF_A_KIND:
                result = handleThreeOfAKindTie((ThreeOfAKind) self, (ThreeOfAKind) other);
                break;
            case TWO_PAIR:
                result = handleTwoPairTie((TwoPair) self, (TwoPair) other);
                break;
            case PAIR:
                result = handlePairTie((Pair) self, (Pair) other);
                break;
            case HIGH_CARD:
                result = handleHighCardTie((HighCard) self, (HighCard) other);
                break;
        }
        return result;
    }

    int handleRoyalFlushTie(RoyalFlush self, RoyalFlush other) {
        return 0;
    }

    int handleStraightFlushTie(StraightFlush self, StraightFlush other) {
        return self.getHighCard().compareTo(other.getHighCard());
    }

    int handleFourOfAKindTie(FourOfAKind self, FourOfAKind other) {
        int result = self.getRank().get().compareTo(other.getRank().get());
        if (result == 0) {
            Rank selfKickerRank = self.getHand().getXOfAKindRank(1).get(0);
            Rank otherKickerRank = other.getHand().getXOfAKindRank(1).get(0);
            result = selfKickerRank.compareTo(otherKickerRank);
        }
        return result;
    }

    int handleFullHouseTie(FullHouse self, FullHouse other) {
        return self.getThreeOfAKindRank().compareTo(other.getThreeOfAKindRank());
    }

    int handleFlushTie(Flush self, Flush other) {
        return compareHand(self, other);
    }

    int handleStraightTie(Straight self, Straight other) {
        return self.getHighRank().compareTo(other.getHighRank());
    }

    int handleThreeOfAKindTie(ThreeOfAKind self, ThreeOfAKind other) {
        int result = self.getRank().get().compareTo(other.getRank().get());
        if (result != 0) {
            return result;
        }

        return compareKickerCards(self, other);
    }

    int handleTwoPairTie(TwoPair self, TwoPair other) {
        List<Rank> selfRanks = self.getRanksSorted();
        List<Rank> otherRanks = other.getRanksSorted();

        int result;
        for (int i = 0; i < 2; i++) {
            result = selfRanks.get(i).compareTo(otherRanks.get(i));
            if (result != 0) {
                return result;
            }
        }

        Rank selfKickerRank = self.getHand().getXOfAKindRank(1).get(0);
        Rank otherKickerRank = other.getHand().getXOfAKindRank(1).get(0);
        return selfKickerRank.compareTo(otherKickerRank);
    }

    int compareKickerCards(HandRanking self, HandRanking other) {
        int result;
        List<Card> selfKickerCards = self
                .getHand()
                .getCards()
                .stream()
                .filter(card -> card.getRank() != self.getRank().get())
                .collect(Collectors.toList());
        selfKickerCards.sort((card1, card2) -> card2.compareTo(card1));

        List<Card> otherKickerCards = other
                .getHand()
                .getCards()
                .stream()
                .filter(card -> card.getRank() != other.getRank().get())
                .collect(Collectors.toList());
        otherKickerCards.sort((card1, card2) -> card2.compareTo(card1));

        for (int i = 0; i < selfKickerCards.size(); i++) {
            result = selfKickerCards.get(i).compareTo(otherKickerCards.get(i));
            if (result != 0) {
                return result;
            }
        }

        result = 0; // hands are the same
        return result;
    }

    int handlePairTie(Pair self, Pair other) {
        int result = self.getRank().get().compareTo(other.getRank().get());
        if (result != 0) {
            return result;
        }

        return compareKickerCards(self, other);
    }

    int handleHighCardTie(HighCard self, HighCard other) {
        return compareHand(self, other);
    }

    private int compareHand(HandRanking self, HandRanking other) {
        List<Card> selfCards = self.getHand().getCardsSorted();
        List<Card> otherCards = other.getHand().getCardsSorted();

        int result;
        for (int i = 0; i < 5; i++) {
            Card selfCard = selfCards.get(i);
            Card otherCard = otherCards.get(i);

            result = selfCard.compareTo(otherCard);

            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}
