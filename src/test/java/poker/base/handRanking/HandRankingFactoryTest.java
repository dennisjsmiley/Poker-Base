package poker.base.handRanking;

import org.junit.Test;
import poker.base.Card;
import poker.base.Hand;
import poker.base.enums.Rank;
import poker.base.enums.Suit;
import poker.base.exception.NotAFullHandException;
import poker.base.exception.NullHandException;
import poker.base.exception.StraightFlushNotSameSuit;
import poker.base.handRanking.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandRankingFactoryTest {

    private HandRankingFactory handRankingFactory = new HandRankingFactory();

    // Royal Flush

    @Test
    public void testHandRankingFactory_RoyalFlush_Spades() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.KING));
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.SPADES, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.TEN));

        Hand hand = new Hand(cards);

        HandRanking handRanking = handRankingFactory.build(hand);
        assertTrue(handRanking instanceof RoyalFlush);

        RoyalFlush royalFlush = (RoyalFlush) handRanking;
        assertEquals(Suit.SPADES, royalFlush.getSuit());
    }

    @Test
    public void testHandRankingFactory_WouldBeRoyalFlush_SpadesAndDiamonds() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.KING));
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.TEN));

        Hand hand = new Hand(cards);

        assertFalse(handRankingFactory.build(hand) instanceof RoyalFlush);
    }

    // Straight Flush

    @Test
    public void testHandRankingFactory_StraightFlush_Spades() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));
        cards.add(new Card(Suit.SPADES, Rank.SIX));

        Hand hand = new Hand(cards);

        HandRanking handRanking = handRankingFactory.build(hand);
        assertTrue(handRanking instanceof StraightFlush);

        StraightFlush straightFlush = (StraightFlush) handRanking;
        assertEquals(Suit.SPADES, straightFlush.getLowCard().getSuit());
        assertEquals(Suit.SPADES, straightFlush.getHighCard().getSuit());
        assertEquals(Rank.SIX, straightFlush.getLowCard().getRank());
        assertEquals(Rank.TEN, straightFlush.getHighCard().getRank());
    }

    @Test
    public void testHandRankingFactory_WouldBeStraightFlush_Spades() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));

        Hand hand = new Hand(cards);

        assertFalse(handRankingFactory.build(hand) instanceof StraightFlush);
    }

    @Test
    public void testHandRankingFactory_WouldBeStraightFlush_SpadesAndDiamonds() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.SIX));

        Hand hand = new Hand(cards);

        assertFalse(handRankingFactory.build(hand) instanceof StraightFlush);
    }

    // Four of a Kind

    @Test
    public void testHandRankingFactory_FourOfAKind_Jacks() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.HEARTS, Rank.JACK));
        cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.JACK));
        cards.add(new Card(Suit.CLUBS, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.ACE));

        Hand hand = new Hand(cards);

        HandRanking handRanking = handRankingFactory.build(hand);
        assertTrue(handRanking instanceof FourOfAKind);

        FourOfAKind fourOfAKind = (FourOfAKind) handRanking;
        assertEquals(Rank.JACK, fourOfAKind.getRank());
    }

    // Full House

    @Test
    public void testHandRankingFactory_FullHouse() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.HEARTS, Rank.TEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        cards.add(new Card(Suit.CLUBS, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));
        cards.add(new Card(Suit.CLUBS, Rank.FIVE));

        Hand hand = new Hand(cards);

        HandRanking handRanking = handRankingFactory.build(hand);
        assertTrue(handRanking instanceof FullHouse);

        FullHouse fullHouse = (FullHouse) handRanking;
        assertEquals(Rank.TEN, fullHouse.getThreeOfAKindRank());
        assertEquals(Rank.FIVE, fullHouse.getTwoOfAKindRank());
    }

    // Flush

    @Test
    public void testHandRankingFactory_Flush() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.SPADES, Rank.FOUR));
        cards.add(new Card(Suit.SPADES, Rank.SIX));
        cards.add(new Card(Suit.SPADES, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.ACE));

        Hand hand = new Hand(cards);

        HandRanking handRanking = handRankingFactory.build(hand);
        assertTrue(handRanking instanceof Flush);

        Flush flush = (Flush) handRanking;
        assertEquals(Suit.SPADES, flush.getSuit());
    }

    @Test
    public void testHandRankingFactory_WouldBeFlush() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.SPADES, Rank.THREE));
        cards.add(new Card(Suit.SPADES, Rank.FOUR));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));
        cards.add(new Card(Suit.SPADES, Rank.SIX));

        Hand hand = new Hand(cards);

        HandRanking handRanking = handRankingFactory.build(hand);
        assertTrue(handRanking instanceof StraightFlush);
    }

    // Straight

    @Test
    public void testHandRankingFactory_Straight() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.HEARTS, Rank.THREE));
        cards.add(new Card(Suit.CLUBS, Rank.FOUR));
        cards.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        cards.add(new Card(Suit.SPADES, Rank.SIX));

        Hand hand = new Hand(cards);

        HandRanking handRanking = handRankingFactory.build(hand);
        assertTrue(handRanking instanceof Straight);

        Straight straight = (Straight) handRanking;
        assertEquals(Rank.TWO, straight.getLowRank());
        assertEquals(Rank.SIX, straight.getHighRank());
    }

    @Test
    public void testHandRankingFactory_AlmostStraight() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.HEARTS, Rank.THREE));
        cards.add(new Card(Suit.CLUBS, Rank.FOUR));
        cards.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));

        Hand hand = new Hand(cards);

        HandRanking handRanking = handRankingFactory.build(hand);
        assertFalse(handRanking instanceof Straight);
    }

    // Three of a Kind

    @Test
    public void testHandRankingFactory_ThreeOfAKind() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        cards.add(new Card(Suit.HEARTS, Rank.THREE));
        cards.add(new Card(Suit.CLUBS, Rank.KING));

        Hand hand = new Hand(cards);

        HandRanking handRanking = handRankingFactory.build(hand);
        assertTrue(handRanking instanceof ThreeOfAKind);

        ThreeOfAKind threeOfAKind = (ThreeOfAKind) handRanking;
        assertEquals(Rank.ACE, threeOfAKind.getRank());
    }

    @Test
    public void testHandRankingFactory_NotThreeOfAKindButFullHouse() throws StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        cards.add(new Card(Suit.HEARTS, Rank.KING));
        cards.add(new Card(Suit.CLUBS, Rank.KING));

        Hand hand = new Hand(cards);

        HandRanking handRanking = handRankingFactory.build(hand);
        assertTrue(handRanking instanceof FullHouse);
    }
}
