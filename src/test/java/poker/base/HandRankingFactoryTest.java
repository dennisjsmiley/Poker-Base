package poker.base;

import org.junit.Test;
import poker.base.enums.Rank;
import poker.base.enums.Suit;
import poker.base.exception.NotAFullHandException;
import poker.base.exception.NullHandException;
import poker.base.exception.StraightFlushNotSameSuit;
import poker.base.handRanking.HandRanking;
import poker.base.handRanking.HandRankingFactory;
import poker.base.handRanking.RoyalFlush;
import poker.base.handRanking.StraightFlush;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandRankingFactoryTest {

    HandRankingFactory handRankingFactory = new HandRankingFactory();

    // Royal Flush

    @Test
    public void testHandRankingFactory_RoyalFlush_Spades() throws NullHandException, NotAFullHandException, StraightFlushNotSameSuit {
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
    public void testHandRankingFactory_WouldBeRoyalFlush_SpadesAndDiamonds() throws NullHandException, NotAFullHandException, StraightFlushNotSameSuit {
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
    public void testHandRankingFactory_StraightFlush_Spades() throws NullHandException, NotAFullHandException, StraightFlushNotSameSuit {
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
    public void testHandRankingFactory_WouldBeStraightFlush_Spades() throws NullHandException, NotAFullHandException, StraightFlushNotSameSuit {
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
    public void testHandRankingFactory_WouldBeStraightFlush_SpadesAndDiamonds() throws NullHandException, NotAFullHandException, StraightFlushNotSameSuit {
        Set<Card> cards = new HashSet<>();

        cards.add(new Card(Suit.SPADES, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.SIX));

        Hand hand = new Hand(cards);

        assertFalse(handRankingFactory.build(hand) instanceof StraightFlush);
    }
}