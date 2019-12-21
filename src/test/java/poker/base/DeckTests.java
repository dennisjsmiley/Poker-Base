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
}
