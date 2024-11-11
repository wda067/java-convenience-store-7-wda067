package store.enums;

public enum Message {

    //환영 메시지
    WELCOME("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),

    //입력 메시지
    INPUT_PRODUCT("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    CONFIRM_MEMBERSHIP("\n멤버십 할인을 받으시겠습니까? (Y/N)"),
    CONFIRM_FREE_ITEM_ADDITION("\n현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n"),
    CONFIRM_PURCHASE_WITHOUT_PROMOTION("\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n"),
    CONFIRM_ADDITIONAL_PURCHASE("\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),

    //출력 메시지
    PURCHASE_SUMMARY_HEADER("\n==============W 편의점================"),
    PRODUCT_HEADER_FORMAT("%-15s\t%-5s\t%-10s%n"),
    PRODUCT_DETAILS_FORMAT("%-15s\t%-5s\t%,-10d%n"),
    FREE_PRODUCTS_HEADER("=============증\t\t정==============="),
    FREE_PRODUCT_DETAILS_FORMAT("%-15s\t%-5d%n"),
    PAYMENT_SUMMARY("===================================="),
    TOTAL_AMOUNT_FORMAT("%-15s\t%-5d\t%,-10d%n"),
    PROMOTION_DISCOUNT_FORMAT("%-20s\t\t%-10s%n"),
    MEMBERSHIP_DISCOUNT_FORMAT("%-20s\t\t%-10s%n"),
    FINAL_AMOUNT_FORMAT("%-15s\t\t%,10d%n"),
    ERROR_PREFIX("[ERROR] "),

    //상품 및 결제 정보 헤더
    PRODUCT_NAME("상품명"),
    PRODUCT_QUANTITY("수량"),
    PRODUCT_PRICE("금액"),
    TOTAL_AMOUNT("총구매액"),
    PROMOTION_DISCOUNT("행사할인"),
    MEMBERSHIP_DISCOUNT("멤버십할인"),
    FINAL_ACCOUNT("내실돈");


    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
