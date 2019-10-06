package poker.base;

import org.junit.Test;
import poker.base.enums.Rank;
import poker.base.enums.Suit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CardTest {

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
}
