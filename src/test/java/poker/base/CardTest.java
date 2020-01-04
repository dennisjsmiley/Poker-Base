package poker.base;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.enums.Rank;
import poker.base.enums.Suit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CardTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testCardEquals() {
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card anotherTwoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        assertEquals(anotherTwoOfSpades, twoOfSpades);

        Card threeOfSpades = new Card(Suit.SPADES, Rank.THREE);
        assertNotEquals(twoOfSpades, threeOfSpades);

        Card nullCard = null;
        assert !twoOfSpades.equals(nullCard);
    }

    @Test
    public void testCardToString() {
        Card card = new Card(Suit.SPADES, Rank.ACE);
        logger.debug("Ace of Spades: '{}'", card);
    }

    @Test
    public void testFromShortCode() {
        Card card = Card.fromShortCode("10d").get();
        logger.debug("Ten of Diamonds: {}", card);
    }
}
