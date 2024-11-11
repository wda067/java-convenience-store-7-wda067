package store.dto;

import static store.enums.Constants.ZERO_VALUE;
import static store.enums.Message.AMOUNT_FORMAT;
import static store.enums.Message.DISCOUNT_FORMAT;
import static store.enums.Message.MINUS_ZERO;

public class PurchaseDto {

    private final int totalCount;
    private final int totalAmount;
    private final int promotionDiscount;
    private final int membershipDiscount;
    private final int finalAmount;

    public PurchaseDto(int totalCount, int totalAmount, int promotionDiscount, int membershipDiscount,
                       int finalAmount) {
        this.totalCount = totalCount;
        this.totalAmount = totalAmount;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.finalAmount = finalAmount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getPromotionDiscount() {
        return formatDiscount(promotionDiscount);
    }

    public String getMembershipDiscount() {
        return formatDiscount(membershipDiscount);
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    private String formatAmount(int amount) {
        return String.format(AMOUNT_FORMAT.getMessage(), amount);
    }

    private String formatDiscount(int discount) {
        if (discount == ZERO_VALUE.getValue()) {
            return MINUS_ZERO.getMessage();
        }
        return String.format(DISCOUNT_FORMAT.getMessage(), formatAmount(Math.abs(discount)));
    }
}
