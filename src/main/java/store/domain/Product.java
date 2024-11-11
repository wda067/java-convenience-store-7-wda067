package store.domain;

import java.text.NumberFormat;
import java.util.Locale;

public class Product {

    private String name;
    private int price;
    private int quantity;
    private int promotionQuantity;
    private Promotion promotion;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        if (promotion == null) {
            this.quantity = quantity;
            return;
        }
        this.promotionQuantity = quantity;

    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotionQuantity(int quantity) {
        this.promotionQuantity = quantity;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void reduceQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public void reducePromotionQuantity(int quantity) {
        this.promotionQuantity -= quantity;
        if (this.promotionQuantity < 0) {
            reduceQuantity(Math.abs(this.promotionQuantity));
            this.promotionQuantity = 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder normalProduct = new StringBuilder();
        StringBuilder promotionProduct = new StringBuilder();
        NumberFormat formatter = NumberFormat.getInstance(Locale.KOREA);  //가격에 쉼표 추가

        normalProduct.append("- ").append(name).append(" ")
                .append(formatter.format(price)).append("원 ");
        if (quantity > 0) {
            normalProduct.append(quantity).append("개 ");
        } else {
            normalProduct.append("재고 없음");
        }
        if (promotion == null) {
            return normalProduct.toString();
        }

        promotionProduct.append("- ").append(name).append(" ")
                .append(formatter.format(price)).append("원 ");
        if (promotionQuantity > 0) {
            promotionProduct.append(promotionQuantity).append("개 ");
        } else {
            promotionProduct.append("재고 없음");
        }
        promotionProduct.append(promotion.getName());
        return promotionProduct.append("\n").append(normalProduct).toString();
    }
}
