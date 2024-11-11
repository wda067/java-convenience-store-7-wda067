package store.enums;

public enum ProductConstants {

    NEW_LINE("\n"),
    DASH("- "),
    BLANK(" "),
    NO_QUANTITY("재고 없음"),
    CURRENCY("원 "),
    PIECES("개 ");

    private final String value;

    ProductConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
