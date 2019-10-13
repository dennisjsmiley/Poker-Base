package poker.base.enums;

public enum HandRanking {
    ROYAL_FLUSH("Royal Flush", 10),
    STRAIGHT_FLUSH("Straight Flush", 9),
    FOUR_OF_A_KIND("Four of a Kind", 8),
    FULL_HOUSE("Full House", 7),
    FLUSH("Flush", 6),
    STRAIGHT("Straight", 5),
    THREE_OF_A_KIND("Three of a Kind", 4),
    TWO_PAIR("Two Pair", 3),
    PAIR("Pair", 2),
    HIGH_CARD("High Card", 1);

    private String stringValue;
    private Integer integerValue;

    HandRanking(String stringValue, Integer intergerValue) {
        this.stringValue = stringValue;
        this.integerValue = intergerValue;
    }

    public Integer toInteger() {
        return integerValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
