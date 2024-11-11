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

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public void setPromotionQuantity(int quantity) {
        this.promotionQuantity = quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void reduceQuantity(int quantity) {
        this.quantity -= quantity;
    }

}
