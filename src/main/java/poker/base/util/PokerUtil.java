package poker.base.util;

import poker.base.Card;

import java.util.*;

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

    public static Set<Set<Card>> computeThreeCardCombinations(List<Card> communityCards) {
        Set<Card> cards = new HashSet<>();
        cards.addAll(communityCards);
        Set<Set<Card>> threeCardCombos = selectCombinations(cards, 3, 0);
        return threeCardCombos;
    }

    public static <T> Set<Set<T>> selectCombinations(Set<T> set, int selectionSize, int recursionLevel) {
        assert selectionSize <= set.size();
        Set<Set<T>> result = new HashSet<>();
        if (set.size() == selectionSize) {
            result.add(set);
            return result;
        } else {
            for (T element1 : set) {
                Set<T> subsetMinusElement1 = new HashSet<>();
                for (T element2 : set) {
                    if (element1 != element2) {
                        subsetMinusElement1.add(element2);
                    }
                }
                result.addAll(selectCombinations(subsetMinusElement1, selectionSize, recursionLevel + 1));
            }
            return result;
        }
    }

}
