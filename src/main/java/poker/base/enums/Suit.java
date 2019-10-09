package poker.base.enums;

import java.util.Optional;

public enum Suit {
    SPADES("Spades"),
    CLUBS("Clubs"),
    DIAMONDS("Diamonds"),
    HEARTS("Hearts");

    private String value;

    Suit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Optional<Suit> fromString(String string) {
        for (Suit suit : values()) {
            if (suit.getValue().equals(string)) {
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
