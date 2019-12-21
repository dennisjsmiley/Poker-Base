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

    private final List<Card> cards;

    public Deck() {
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
}
