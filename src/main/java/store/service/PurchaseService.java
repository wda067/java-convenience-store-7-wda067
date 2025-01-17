package store.service;

import static store.enums.Constants.MEMBERSHIP_DISCOUNT_MAX_VALUE;
import static store.enums.Constants.MINUS_ONE_VALUE;
import static store.enums.Constants.ZERO_VALUE;
import static store.enums.ExceptionMessage.OUT_OF_QUANTITY;
import static store.enums.ExceptionMessage.PRODUCT_NOT_FOUND;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.domain.Product;
import store.domain.Promotion;
import store.dto.ProductDto;
import store.dto.PromotionDto;
import store.dto.PurchaseDto;
import store.repository.ProductRepository;

public class PurchaseService {

    private static final double THIRTY_PERCENT_DISCOUNT = 0.3;
    private final ProductRepository productRepository;
    private Map<String, Integer> cart;

    public PurchaseService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void initCart(Map<String, Integer> cart, LocalDate date) {
        validateProduct(cart, date);
        this.cart = cart;
    }

    private void validateProduct(Map<String, Integer> cart, LocalDate date) {
        for (Entry<String, Integer> entry : cart.entrySet()) {
            Product product = getProductByName(entry.getKey());
            if (isPromotionApplicable(product, date)) {
                if (entry.getValue() > product.getQuantity() + product.getPromotionQuantity()) {
                    throw new IllegalArgumentException(OUT_OF_QUANTITY.getMessage());
                }
                continue;
            }
            if (entry.getValue() > product.getQuantity()) {
                throw new IllegalArgumentException(OUT_OF_QUANTITY.getMessage());
            }
        }
    }

    public List<Product> getProducts() {
        return productRepository.findAllProducts();
    }

    public Map<String, Integer> getCart() {
        return cart;
    }

    public List<ProductDto> getProductDtos() {
        List<ProductDto> productPurchaseDtos = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            Product product = getProductByName(productName);
            int pricePerUnit = product.getPrice() * quantity;
            productPurchaseDtos.add(new ProductDto(productName, quantity, pricePerUnit));
        }
        return productPurchaseDtos.reversed();
    }

    public List<PromotionDto> applyApplicablePromotions(LocalDate date) {
        List<PromotionDto> promotions = new ArrayList<>();
        for (Entry<String, Integer> entry : cart.entrySet()) {
            String item = entry.getKey();
            int purchaseCount = entry.getValue();
            Product product = productRepository.findProductByName(item)
                    .orElseThrow();
            if (isPromotionApplicable(product, date)) {
                PromotionDto promotionDto = applyPromotion(product, purchaseCount);
                promotions.add(promotionDto);
            }
        }
        return promotions.reversed();
    }

    public PromotionDto applyPromotion(Product product, int purchaseQuantity) {
        PromotionDto promotionDto = new PromotionDto(product.getName());
        Promotion promotion = product.getPromotion();

        //프로모션 재고가 충분한 경우
        if (purchaseQuantity < product.getPromotionQuantity()) {
            return getDtoWhenQuantityIsSufficient(purchaseQuantity, promotion, promotionDto);
        }
        //프로모션 재고가 부족한 경우
        return getDtoWhenQuantityIsInsufficient(product, purchaseQuantity, promotion, promotionDto);
    }

    private PromotionDto getDtoWhenQuantityIsSufficient(int purchaseQuantity, Promotion promotion,
                                                        PromotionDto promotionDto) {
        int free = purchaseQuantity / (promotion.getBuyQuantity() + promotion.getGetQuantity());
        int remainder = purchaseQuantity % (promotion.getBuyQuantity() + promotion.getGetQuantity());
        //추가 증정 가능
        if (remainder == promotion.getBuyQuantity()) {
            promotionDto.setFreeCount(free);
            promotionDto.setAvailablePromotionQuantity(promotion.getGetQuantity());
            return promotionDto;
        }

        promotionDto.setFreeCount(free);
        return promotionDto;
    }

    private PromotionDto getDtoWhenQuantityIsInsufficient(Product product, int purchaseQuantity,
                                                          Promotion promotion,
                                                          PromotionDto promotionDto) {
        int free = product.getPromotionQuantity() / (promotion.getBuyQuantity() + promotion.getGetQuantity());
        promotionDto.setFreeCount(free);
        promotionDto.setNonPromotionQuantity(
                purchaseQuantity - (promotion.getBuyQuantity() + promotion.getGetQuantity()) * free);
        return promotionDto;
    }

    public void updateQuantity(LocalDate date) {
        for (Entry<String, Integer> entry : cart.entrySet()) {
            Product product = productRepository.findProductByName(entry.getKey())
                    .orElseThrow();
            if (isPromotionApplicable(product, date)) {
                product.reducePromotionQuantity(entry.getValue());
                return;
            }
            product.reduceQuantity(entry.getValue());
        }
    }

    public PurchaseDto calculatePurchase(List<PromotionDto> promotions, boolean hasMembership) {
        int totalCount = calculateTotalCount();
        int totalAmount = calculateTotalAmount();
        int promotionDiscount = calculatePromotionDiscount(promotions);
        int membershipDiscount = ZERO_VALUE.getValue();
        if (hasMembership) {
            membershipDiscount = calculateMembershipDiscount(totalAmount, promotions);
        }
        int finalAmount = totalAmount + promotionDiscount + membershipDiscount;
        return new PurchaseDto(totalCount, totalAmount, promotionDiscount, membershipDiscount, finalAmount);
    }

    private int calculateTotalCount() {
        return cart.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int calculateTotalAmount() {
        return cart.entrySet().stream()
                .mapToInt(entry -> {
                    Product product = getProductByName(entry.getKey());
                    return product.getPrice() * entry.getValue();
                })
                .sum();
    }

    private int calculatePromotionDiscount(List<PromotionDto> promotionDtos) {
        return promotionDtos.stream()
                .mapToInt(promotion -> {
                    Product product = getProductByName(promotion.getName());
                    return product.getPrice() * promotion.getFreeCount();
                })
                .sum() * MINUS_ONE_VALUE.getValue();
    }

    private int calculateMembershipDiscount(int totalAmount, List<PromotionDto> promotionDtos) {
        int membershipDiscount = promotionDtos.stream()
                .mapToInt(this::calculateIndividualMembershipDiscount)
                .sum() - totalAmount;

        membershipDiscount = (int) (membershipDiscount * THIRTY_PERCENT_DISCOUNT);
        return Math.max(membershipDiscount, MINUS_ONE_VALUE.getValue() * MEMBERSHIP_DISCOUNT_MAX_VALUE.getValue());
    }

    private int calculateIndividualMembershipDiscount(PromotionDto promotionDto) {
        Product product = getProductByName(promotionDto.getName());
        Promotion promotion = product.getPromotion();
        int promotionCount = promotion.getBuyQuantity() + promotion.getGetQuantity();
        return product.getPrice() * promotionDto.getFreeCount() * promotionCount;
    }

    private Product getProductByName(String name) {
        return productRepository.findProductByName(name)
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND.getMessage()));
    }

    private boolean isPromotionApplicable(Product product, LocalDate date) {
        Promotion promotion = product.getPromotion();
        if (promotion == null) {
            return false;
        }
        LocalDate startDate = promotion.getStartDate();
        LocalDate endDate = promotion.getEndDate();
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate));
    }
}
