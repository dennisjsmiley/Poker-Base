package poker.base.enums;

public enum HandRanking {
    HIGH_CARD("High Card", 1),
    PAIR("Pair", 2),
    TWO_PAIR("Two Pair", 3),
    THREE_OF_A_KIND("Three of a Kind", 4),
    STRAIGHT("Straight", 5),
    FLUSH("Flush", 6),
    FULL_HOUSE("Full House", 7),
    FOUR_OF_A_KIND("Four of a Kind", 8),
    STRAIGHT_FLUSH("Straight Flush", 9),
    ROYAL_FLUSH("Royal Flush", 10);

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
