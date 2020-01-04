package poker.base.util;

import poker.base.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PokerUtil {

    public static String toCardShortCodes(Set<Card> cards) {
        List<Card> cardList = new ArrayList<>();
        cardList.addAll(cards);
        return toCardShortCodes(cardList);
    }

    public static String toCardShortCodes(List<Card> cards) {
        List<String> cardShortCodes = new ArrayList<>();
        cards.forEach(card -> cardShortCodes.add(card.toShortCode()));
        return String.join(",", cardShortCodes);
    }
}
