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

    public Deck() {
        index = 0;
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> getFlop() {
        List<Card> flop = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            flop.add(draw());
        }
        return flop;
    }

    public Card draw() {
        Card card = cards.get(index);
        index++;
        return card;
    }

    int getIndex() {
        return index;
    }
}
