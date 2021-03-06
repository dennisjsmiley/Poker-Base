package poker.base;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.enums.Rank;
import poker.base.enums.Suit;
import poker.base.handRanking.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // Royal Flush

    @Test
    public void testHand_GetHandRanking_RoyalFlush_Spades() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.KING));
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.SPADES, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.TEN));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof RoyalFlush);

        RoyalFlush royalFlush = (RoyalFlush) handRanking;
        assertEquals(Suit.SPADES, royalFlush.getSuit());

        logger.debug("Royal Flush (Spades): '{}'", royalFlush);
    }

    @Test
    public void testHand_GetHandRanking_WouldBeRoyalFlush_SpadesAndDiamonds() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.KING));
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.TEN));

        Hand hand = new Hand(cards);

        assertFalse(hand.getHandRanking() instanceof RoyalFlush);
    }

    // Straight Flush

    @Test
    public void testHand_GetHandRanking_StraightFlush_Spades() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));
        cards.add(new Card(Suit.SPADES, Rank.SIX));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof StraightFlush);

        StraightFlush straightFlush = (StraightFlush) handRanking;
        assertEquals(Suit.SPADES, straightFlush.getLowCard().getSuit());
        assertEquals(Suit.SPADES, straightFlush.getHighCard().getSuit());
        assertEquals(Rank.SIX, straightFlush.getLowCard().getRank());
        assertEquals(Rank.TEN, straightFlush.getHighCard().getRank());

        logger.debug("Straight Flush (Ten of Spades, Six of Spades): '{}'", straightFlush);
    }

    @Test
    public void testHand_GetHandRanking_WouldBeStraightFlush_Spades() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));

        Hand hand = new Hand(cards);

        assertFalse(hand.getHandRanking() instanceof StraightFlush);
    }

    @Test
    public void testHand_GetHandRanking_WouldBeStraightFlush_SpadesAndDiamonds() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.SIX));

        Hand hand = new Hand(cards);

        assertFalse(hand.getHandRanking() instanceof StraightFlush);
    }

    // Four of a Kind

    @Test
    public void testHand_GetHandRanking_FourOfAKind_Jacks() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.HEARTS, Rank.JACK));
        cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.JACK));
        cards.add(new Card(Suit.CLUBS, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.ACE));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof FourOfAKind);

        FourOfAKind fourOfAKind = (FourOfAKind) handRanking;
        assertEquals(Rank.JACK, fourOfAKind.getRank().get());

        logger.debug("Four of a Kind (Jack): '{}'", fourOfAKind);
    }

    // Full House

    @Test
    public void testHand_GetHandRanking_FullHouse() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.HEARTS, Rank.TEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        cards.add(new Card(Suit.CLUBS, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));
        cards.add(new Card(Suit.CLUBS, Rank.FIVE));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof FullHouse);

        FullHouse fullHouse = (FullHouse) handRanking;
        assertEquals(Rank.TEN, fullHouse.getThreeOfAKindRank());
        assertEquals(Rank.FIVE, fullHouse.getTwoOfAKindRank());

        logger.debug("Full House (3x Ten, 2x Five): '{}'", fullHouse);
    }

    // Flush

    @Test
    public void testHand_GetHandRanking_Flush() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.SPADES, Rank.FOUR));
        cards.add(new Card(Suit.SPADES, Rank.SIX));
        cards.add(new Card(Suit.SPADES, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.ACE));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof Flush);

        Flush flush = (Flush) handRanking;
        assertEquals(Suit.SPADES, flush.getSuit());

        logger.debug("Flush (Spades): '{}'", flush);
    }

    @Test
    public void testHand_GetHandRanking_WouldBeFlush() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.SPADES, Rank.THREE));
        cards.add(new Card(Suit.SPADES, Rank.FOUR));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));
        cards.add(new Card(Suit.SPADES, Rank.SIX));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof StraightFlush);
    }

    // Straight

    @Test
    public void testHand_GetHandRanking_Straight() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.HEARTS, Rank.THREE));
        cards.add(new Card(Suit.CLUBS, Rank.FOUR));
        cards.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        cards.add(new Card(Suit.SPADES, Rank.SIX));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof Straight);

        Straight straight = (Straight) handRanking;
        assertEquals(Rank.TWO, straight.getLowRank());
        assertEquals(Rank.SIX, straight.getHighRank());

        logger.debug("Straight (Six, Two): '{}'", straight);
    }

    @Test
    public void testHand_GetHandRanking_AlmostStraight() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.HEARTS, Rank.THREE));
        cards.add(new Card(Suit.CLUBS, Rank.FOUR));
        cards.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertFalse(handRanking instanceof Straight);
    }

    // Three of a Kind

    @Test
    public void testHand_GetHandRanking_ThreeOfAKind() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        cards.add(new Card(Suit.HEARTS, Rank.THREE));
        cards.add(new Card(Suit.CLUBS, Rank.KING));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof ThreeOfAKind);

        ThreeOfAKind threeOfAKind = (ThreeOfAKind) handRanking;
        assertEquals(Rank.ACE, threeOfAKind.getRank().get());

        logger.debug("Three of a Kind (Ace): '{}'", threeOfAKind);
    }

    @Test
    public void testHand_GetHandRanking_NotThreeOfAKindButFullHouse() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        cards.add(new Card(Suit.HEARTS, Rank.KING));
        cards.add(new Card(Suit.CLUBS, Rank.KING));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof FullHouse);
    }

    // Two Pair

    @Test
    public void testHand_GetHandRanking_TwoPair() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.CLUBS, Rank.KING));
        cards.add(new Card(Suit.HEARTS, Rank.KING));
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.THREE));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof TwoPair);

        TwoPair twoPair = (TwoPair) handRanking;
        Set<Rank> ranks = new HashSet<>();
        ranks.add(Rank.KING);
        ranks.add(Rank.ACE);
        assertEquals(ranks, twoPair.getRanks());

        logger.debug("Two Pair ([King, Ace]), '{}'", twoPair);
    }

    // Pair

    @Test
    public void testHand_GetHandRanking_Pair() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.THREE));
        cards.add(new Card(Suit.CLUBS, Rank.FOUR));
        cards.add(new Card(Suit.HEARTS, Rank.FIVE));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof Pair);

        Pair pair = (Pair) handRanking;
        assertEquals(Rank.ACE, pair.getRank().get());

        logger.debug("Pair (Ace): '{}'", pair);
    }

    // High Card

    @Test
    public void testHand_GetHandRanking_HighCard() {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards.add(new Card(Suit.CLUBS, Rank.THREE));
        cards.add(new Card(Suit.CLUBS, Rank.FOUR));
        cards.add(new Card(Suit.HEARTS, Rank.FIVE));

        Hand hand = new Hand(cards);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof HighCard);

        HighCard highCard = (HighCard) handRanking;
        assertEquals(Rank.ACE, highCard.getRank().get());

        logger.debug("High Card (Ace): '{}'", highCard);
    }

    // Short Codes

    @Test
    public void testHand_FromShortCodes() {
        Hand hand = Hand.fromShortCodes("as,ks,qs,js,10s");
        logger.debug("hand: {}", hand);

        HandRanking handRanking = hand.getHandRanking();
        assertTrue(handRanking instanceof RoyalFlush);

        logger.debug("handRanking: {}", handRanking);

        RoyalFlush royalFlush = (RoyalFlush) handRanking;
        assertEquals(Suit.SPADES, royalFlush.getSuit());
    }

    @Test
    public void testHand_getHands() {
        List<Card> communityCards = Arrays.asList(
                new Card(Suit.HEARTS, Rank.SEVEN),
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.QUEEN),
                new Card(Suit.HEARTS, Rank.FOUR),
                new Card(Suit.SPADES, Rank.TEN)
        );

        Set<Card> holdCards = new HashSet<>();
        holdCards.addAll(Arrays.asList(
                new Card(Suit.SPADES, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.QUEEN)
        ));

        String[] expectedHandShortCodes = {
                "as,ad,qd,qc,10s",
                "as,ad,qd,qc,7h",
                "as,ad,qd,qc,4h",
                "as,ad,qd,10s,7h",
                "as,ad,qd,10s,4h",
                "as,ad,qd,7h,4h",
                "as,qd,qc,10s,7h",
                "as,qd,qc,10s,4h",
                "as,qd,qc,7h,4h",
                "as,qd,10s,7h,4h"
        };

        List<Hand> expectedHands = new ArrayList<>();
        for (String shortCode : expectedHandShortCodes) {
            expectedHands.add(Hand.fromShortCodes(shortCode));
        }

        List<Hand> actualHands = Hand.getHands(holdCards, communityCards);

        assertEquals(expectedHands, actualHands);

        for (int i = 0; i < actualHands.size(); i++) {
            Hand hand = actualHands.get(i);
            logger.debug("i: {}, hand: {}, handRanking: {}", i, hand, hand.getHandRanking());
        }
    }

    @Test
    public void testThreeOfAKind() {
        Hand hand = Hand.fromShortCodes("6s,6d,6h,3s,2c");
        logger.debug("hand: {}, ranking: {}", hand, hand.getHandRanking());

        assertTrue(hand.getHandRanking() instanceof ThreeOfAKind);
    }
}
