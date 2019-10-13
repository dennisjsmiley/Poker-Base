package poker.base.enums;

import java.util.Optional;

public enum Rank {
    TWO("Two", 2, "2"),
    THREE("Three", 3, "3"),
    FOUR("Four", 4, "4"),
    FIVE("Five", 5, "5"),
    SIX("Six", 6, "6"),
    SEVEN("Seven", 7, "7"),
    EIGHT("Eight", 8, "8"),
    NINE("Nine", 9, "9"),
    TEN("Ten", 10, "10"),
    JACK("Jack", 11, "j"),
    QUEEN("Queen", 12, "q"),
    KING("King", 13, "k"),
    ACE("Ace", 14, "a");

    private String rankStringValue;
    private Integer rankIntegerValue;
    private String shortCode;

    Rank(String stringValue, Integer integerValue, String shortCode) {
        this.rankStringValue = stringValue;
        this.rankIntegerValue = integerValue;
        this.shortCode = shortCode;
    }

    public Integer toInteger() {
        return rankIntegerValue;
    }

    @Override
    public String toString() {
        return rankStringValue;
    }

    public String getShortCode() {
        return shortCode;
    }

    public static Optional<Rank> fromShortCode(String string) {
        for (Rank rank : Rank.values()) {
            if (rank.getShortCode().equals(string)) {
                return Optional.of(rank);
            }
        }
        return Optional.empty();
    }
}
