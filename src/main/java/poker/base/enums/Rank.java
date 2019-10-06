package poker.base.enums;

public enum Rank {
    ONE("One", 1),
    TWO("Two", 2),
    THREE("Three", 3),
    FOUR("Four", 4),
    FIVE("Five", 5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    JACK("Jack", 11),
    QUEEN("Queen", 12),
    KING("King", 13),
    ACE("Ace", 14);

    private String rankStringValue;
    private Integer rankIntegerValue;

    private Rank(String stringValue, Integer integerValue) {
        this.rankStringValue = stringValue;
        this.rankIntegerValue = integerValue;
    }

    public String getRankStringValue() {
        return rankStringValue;
    }

    public Integer getRankIntegerValue() {
        return rankIntegerValue;
    }
}
