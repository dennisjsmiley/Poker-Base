package poker.base.enums;

import java.util.Optional;

public enum Suit {
    SPADES("Spades", "s"),
    CLUBS("Clubs", "c"),
    DIAMONDS("Diamonds", "d"),
    HEARTS("Hearts", "h");

    private String value;
    private String shortCode;

    Suit(String value, String shortCode) {
        this.value = value;
        this.shortCode = shortCode;
    }

    public String getValue() {
        return value;
    }

    public static Optional<Suit> fromString(String string) {
        for (Suit suit : values()) {
            if (suit.getValue().equals(string)) {
                return Optional.of(suit);
            }
        }
        return Optional.empty();
    }

    public String getShortCode() {
        return shortCode;
    }

    public static Optional<Suit> fromShortCode(String string) {
        for (Suit suit : values()) {
            if (suit.getShortCode().equals(string)) {
                return Optional.of(suit);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return value;
    }
}
