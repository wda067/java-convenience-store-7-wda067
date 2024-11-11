package store.dto;

public class PromotionDto {

    private final String name;
    private int freeCount;  //증정 수량
    private int availablePromotionQuantity;  //추가로 증정 가능한 수량
    private int nonPromotionQuantity;  //프로모션 미적용 수량

    public PromotionDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public int getAvailablePromotionQuantity() {
        return availablePromotionQuantity;
    }

    public int getNonPromotionQuantity() {
        return nonPromotionQuantity;
    }

    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }

    public void setAvailablePromotionQuantity(int availablePromotionQuantity) {
        this.availablePromotionQuantity = availablePromotionQuantity;
    }

    public void setNonPromotionQuantity(int nonPromotionQuantity) {
        this.nonPromotionQuantity = nonPromotionQuantity;
    }
}
