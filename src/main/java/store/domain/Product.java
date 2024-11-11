package store.domain;

import static store.enums.Constants.ZERO_VALUE;
import static store.enums.ProductConstants.BLANK;
import static store.enums.ProductConstants.CURRENCY;
import static store.enums.ProductConstants.DASH;
import static store.enums.ProductConstants.NEW_LINE;
import static store.enums.ProductConstants.NO_QUANTITY;
import static store.enums.ProductConstants.PIECES;

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

    public void setPromotionQuantity(int quantity) {
        this.promotionQuantity = quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void reduceQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public void reducePromotionQuantity(int quantity) {
        this.promotionQuantity -= quantity;
        if (this.promotionQuantity < ZERO_VALUE.getValue()) {
            reduceQuantity(Math.abs(this.promotionQuantity));
            this.promotionQuantity = ZERO_VALUE.getValue();
        }
    }

    @Override
    public String toString() {
        StringBuilder normalProduct = new StringBuilder();
        StringBuilder promotionProduct = new StringBuilder();
        NumberFormat formatter = NumberFormat.getInstance(Locale.KOREA);

        normalProduct.append(DASH.getValue()).append(name).append(BLANK.getValue())
                .append(formatter.format(price)).append(CURRENCY.getValue());
        if (quantity > ZERO_VALUE.getValue()) {
            normalProduct.append(quantity).append(PIECES.getValue());
        } else {
            normalProduct.append(NO_QUANTITY.getValue());
        }
        if (promotion == null) {
            return normalProduct.toString();
        }

        return getPromotionProduct(promotionProduct, formatter, normalProduct);
    }

    private String getPromotionProduct(StringBuilder promotionProduct, NumberFormat formatter, StringBuilder normalProduct) {
        promotionProduct.append(DASH.getValue()).append(name).append(BLANK.getValue())
                .append(formatter.format(price)).append(CURRENCY.getValue());
        if (promotionQuantity > ZERO_VALUE.getValue()) {
            promotionProduct.append(promotionQuantity).append(PIECES.getValue());
        } else {
            promotionProduct.append(NO_QUANTITY.getValue()).append(BLANK.getValue());
        }
        promotionProduct.append(promotion.getName());
        return promotionProduct.append(NEW_LINE.getValue()).append(normalProduct).toString();
    }
}
