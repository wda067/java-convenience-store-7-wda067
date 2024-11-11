package store.view;

import java.util.List;
import store.domain.Product;
import store.dto.ProductDto;
import store.dto.PromotionDto;
import store.dto.PurchaseDto;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
    }

    public void printProducts(List<Product> products) {
        products.forEach(System.out::println);
    }

    public void printPurchasedProducts(List<ProductDto> productDtos) {
        System.out.println("\n==============W 편의점================");
        System.out.printf("%-15s\t%-5s\t%-10s%n", "상품명", "수량", "금액");
        for (ProductDto productDto : productDtos) {
            System.out.printf("%-15s\t%-5s\t%,-10d%n",
                    productDto.getName(),
                    productDto.getQuantity(),
                    productDto.getPrice());
        }
    }

    public void printFreeProducts(List<PromotionDto> freeProducts) {
        System.out.println("=============증\t\t정===============");
        freeProducts.stream()
                .filter(product -> product.getFreeCount() > 0)
                .forEach(product -> System.out.printf("%-15s\t%-5d%n", product.getName(), product.getFreeCount()));
    }

    public void printPaymentSummary(PurchaseDto purchaseDto) {
        System.out.println("====================================");
        System.out.printf("%-15s\t%-5d\t%,-10d%n",
                "총구매액",
                purchaseDto.getTotalCount(),
                purchaseDto.getTotalAmount());

        System.out.printf("%-20s\t\t%,-10d%n", "행사할인", purchaseDto.getPromotionDiscount());
        System.out.printf("%-20s\t\t%,-10d%n", "멤버십할인", purchaseDto.getMembershipDiscount());
        System.out.printf("%-15s\t\t%,10d%n", "내실돈", purchaseDto.getFinalAmount());
    }

    public void printExceptionMessage(String message) {
        System.out.println("exception message: " + message);
    }
}
