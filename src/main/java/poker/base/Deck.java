package poker.base;

import poker.base.enums.Rank;
import poker.base.enums.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by djsmiley on 12/21/19.
 */
public class Deck {

    private int index;
    private final List<Card> cards;
    private final List<Card> flop;

    public Deck() {
        index = 0;
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
        this.cards = Collections.unmodifiableList(cards);
        flop = new ArrayList<>();
        shuffle();
    }

    public void shuffle() {
        synchronized(cards) {
            Collections.shuffle(cards);
            index = 0;
        }
    }

    public List<Card> getCards() {
        synchronized(cards) {
            return cards;
        }
    }

    public List<Card> getFlop() {
        synchronized(flop) {
            if (flop.isEmpty()) {
                for (int i = 0; i < 3; i++) {
                    flop.add(draw());
                }
            }
        }
        return flop;
    }

    public Card draw() {
        synchronized(cards) {
            Card card = cards.get(index);
            index++;
            return card;
        }
    }
}
