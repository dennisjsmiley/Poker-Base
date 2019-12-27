package poker.base;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
        int numPlayers = 5;
        Deck deck = new Deck();
        List<Card> cards = deck.getCards();
        assertEquals(52, cards.size());

        Map<Integer, Set<Card>> playerCards = new HashMap<>();
        for (int playerNum = 0; playerNum < numPlayers; playerNum++) {
            playerCards.put(playerNum, new HashSet<>());
            playerCards.get(playerNum).add(deck.draw());
            playerCards.get(playerNum).add(deck.draw());
        }

        List<Card> flop = deck.getFlop();
        assertEquals(3, flop.size());

        int flopStartIndex = numPlayers * 2;
        for (int i = flopStartIndex; i < flopStartIndex + 3; i++) {
            assertEquals(cards.get(i), flop.get(i - flopStartIndex));
        }

        flop = deck.getFlop();
        assertEquals(3, flop.size());

        for (int i = flopStartIndex; i < flopStartIndex + 3; i++) {
            assertEquals(cards.get(i), flop.get(i - flopStartIndex));
        }

        Card card = deck.draw();
        assertEquals(cards.get(flopStartIndex + 3), card);
    }

    @Test
    public void testDraw() {
        Deck deck = new Deck();
        List<Card> cards = deck.getCards();
        assertEquals(52, cards.size());

        Card card = deck.draw();
        assertEquals(cards.get(0), card);

        card = deck.draw();
        assertEquals(cards.get(1), card);
    }
}
