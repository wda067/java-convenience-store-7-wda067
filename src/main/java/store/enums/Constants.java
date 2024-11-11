package store.enums;

public enum Constants {
    ZERO_VALUE(0),
    ONE_VALUE(1),
    TWO_VALUE(2),
    ONE_HUNDRED_VALUE(100),
    MINUS_ONE_VALUE(-1),
    MEMBERSHIP_DISCOUNT_MAX_VALUE(8_000),

    BONUS_CONDITION_MATCH_COUNT(5);

    private final int value;

    Constants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
