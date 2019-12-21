package poker.base;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by djsmiley on 12/21/19.
 */
public class DeckTests {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testNewDeck() {
        Deck deck = new Deck();
        int i = 0;
        List<Card> cards = deck.getCards();
        for (Card card : cards) {
            logger.info("i: {}, card: {}", i, card);
            i++;
        }
        assertEquals(52, cards.size());
    }

    @Test
    public void testGetFlop() {
        Deck deck = new Deck();
        List<Card> cards = deck.getCards();
        assertEquals(52, cards.size());

        List<Card> flop = deck.getFlop();
        assertEquals(3, flop.size());

        for (int i = 0; i < 3; i++) {
            assertEquals(cards.get(i), flop.get(i));
        }

        assertEquals(3, deck.getIndex());

        flop = deck.getFlop();
        assertEquals(3, flop.size());

        for (int i = 0; i < 3; i++) {
            assertEquals(flop.get(i), cards.get(3 + i));
        }

        assertEquals(6, deck.getIndex());

        Card card = deck.draw();
        assertEquals(7, deck.getIndex());
        assertEquals(cards.get(6), card);
    }
}
