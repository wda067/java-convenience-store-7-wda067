package store.view;

import static store.enums.Message.ERROR_PREFIX;
import static store.enums.Message.FINAL_ACCOUNT;
import static store.enums.Message.FINAL_AMOUNT_FORMAT;
import static store.enums.Message.FREE_PRODUCTS_HEADER;
import static store.enums.Message.FREE_PRODUCT_DETAILS_FORMAT;
import static store.enums.Message.MEMBERSHIP_DISCOUNT;
import static store.enums.Message.MEMBERSHIP_DISCOUNT_FORMAT;
import static store.enums.Message.PAYMENT_SUMMARY;
import static store.enums.Message.PRODUCT_DETAILS_FORMAT;
import static store.enums.Message.PRODUCT_HEADER_FORMAT;
import static store.enums.Message.PRODUCT_NAME;
import static store.enums.Message.PRODUCT_PRICE;
import static store.enums.Message.PRODUCT_QUANTITY;
import static store.enums.Message.PROMOTION_DISCOUNT;
import static store.enums.Message.PROMOTION_DISCOUNT_FORMAT;
import static store.enums.Message.PURCHASE_SUMMARY_HEADER;
import static store.enums.Message.TOTAL_AMOUNT;
import static store.enums.Message.TOTAL_AMOUNT_FORMAT;
import static store.enums.Message.WELCOME;

import java.util.List;
import store.domain.Product;
import store.dto.ProductDto;
import store.dto.PromotionDto;
import store.dto.PurchaseDto;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println(WELCOME.getMessage());
    }

    public void printProducts(List<Product> products) {
        products.forEach(System.out::println);
    }

    public void printPurchasedProducts(List<ProductDto> productDtos) {
        System.out.println(PURCHASE_SUMMARY_HEADER.getMessage());
        System.out.printf(PRODUCT_HEADER_FORMAT.getMessage(), PRODUCT_NAME.getMessage(), PRODUCT_QUANTITY.getMessage(),
                PRODUCT_PRICE.getMessage());
        for (ProductDto productDto : productDtos) {
            System.out.printf(PRODUCT_DETAILS_FORMAT.getMessage(),
                    productDto.getName(),
                    productDto.getQuantity(),
                    productDto.getPrice());
        }
    }

    public void printFreeProducts(List<PromotionDto> freeProducts) {
        System.out.println(FREE_PRODUCTS_HEADER.getMessage());
        freeProducts.stream()
                .filter(product -> product.getFreeCount() > 0)
                .forEach(product -> System.out.printf(FREE_PRODUCT_DETAILS_FORMAT.getMessage(), product.getName(),
                        product.getFreeCount()));
    }

    public void printPaymentSummary(PurchaseDto purchaseDto) {
        System.out.println(PAYMENT_SUMMARY.getMessage());
        System.out.printf(TOTAL_AMOUNT_FORMAT.getMessage(),
                TOTAL_AMOUNT.getMessage(),
                purchaseDto.getTotalCount(),
                purchaseDto.getTotalAmount());

        System.out.printf(PROMOTION_DISCOUNT_FORMAT.getMessage(), PROMOTION_DISCOUNT.getMessage(),
                purchaseDto.getPromotionDiscount());
        System.out.printf(MEMBERSHIP_DISCOUNT_FORMAT.getMessage(), MEMBERSHIP_DISCOUNT.getMessage(),
                purchaseDto.getMembershipDiscount());
        System.out.printf(FINAL_AMOUNT_FORMAT.getMessage(), FINAL_ACCOUNT.getMessage(), purchaseDto.getFinalAmount());
    }

    public void printExceptionMessage(String message) {
        System.out.println(ERROR_PREFIX.getMessage() + message);
    }
}
