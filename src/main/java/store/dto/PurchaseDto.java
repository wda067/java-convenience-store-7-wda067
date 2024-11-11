package store.dto;

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

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }
}
