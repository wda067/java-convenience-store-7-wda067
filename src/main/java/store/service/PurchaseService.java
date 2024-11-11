package store.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.domain.Product;
import store.domain.Promotion;
import store.dto.PromotionDto;
import store.dto.PurchaseDto;
import store.repository.ProductRepository;

public class PurchaseService {

    private final ProductRepository productRepository;
    private Map<String, Integer> productInventory;

    public PurchaseService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void initInventory(Map<String, Integer> productInventory) {
        this.productInventory = productInventory;
    }

    public List<PromotionDto> applyApplicablePromotions(LocalDate date) {
        List<PromotionDto> promotions = new ArrayList<>();
        for (Entry<String, Integer> entry : productInventory.entrySet()) {
            String item = entry.getKey();
            int purchaseCount = entry.getValue();
            Product product = productRepository.findProductByName(item)
                    .orElseThrow();
            if (isPromotionApplicable(product, date)) {
                PromotionDto promotionDto = applyPromotion(product, purchaseCount);
                promotions.add(promotionDto);
            }
        }
        return promotions;
    }

    public PromotionDto applyPromotion(Product product, int purchaseQuantity) {
        PromotionDto promotionDto = new PromotionDto(product.getName());
        Promotion promotion = product.getPromotion();

        //프로모션 재고가 충분한 경우
        if (purchaseQuantity < product.getPromotionQuantity()) {
            return getPromotionDto(purchaseQuantity, promotion, promotionDto);
        }

        //프로모션 재고가 부족한 경우
        int free = product.getPromotionQuantity() / (promotion.getBuyQuantity() + promotion.getGetQuantity());
        promotionDto.setFreeCount(free);
        promotionDto.setNonPromotionQuantity(purchaseQuantity - (promotion.getBuyQuantity() + promotion.getGetQuantity()) * free);
        return promotionDto;
    }

    private PromotionDto getPromotionDto(int purchaseQuantity, Promotion promotion, PromotionDto promotionDto) {
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

    public List<Product> getProducts() {
        return productRepository.findAllProducts();
    }

    public void updateQuantity(LocalDate date) {
        for (Entry<String, Integer> entry : productInventory.entrySet()) {
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
        int membershipDiscount = 0;
        if (hasMembership) {
            membershipDiscount = calculateMembershipDiscount(totalAmount, promotions);
        }
        int finalAmount = totalAmount + promotionDiscount + membershipDiscount;
        return new PurchaseDto(totalCount, totalAmount, promotionDiscount, membershipDiscount, finalAmount);
    }

    private int calculateTotalCount() {
        return productInventory.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int calculateTotalAmount() {
        return productInventory.entrySet().stream()
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
                .sum() * -1;
    }

    private int calculateMembershipDiscount(int totalAmount, List<PromotionDto> promotionDtos) {
        int membershipDiscount = promotionDtos.stream()
                .mapToInt(this::calculateIndividualMembershipDiscount)
                .sum() - totalAmount;

        membershipDiscount = (int) (membershipDiscount * 0.3);
        return Math.max(membershipDiscount, -8000);
    }

    private int calculateIndividualMembershipDiscount(PromotionDto promotionDto) {
        Product product = getProductByName(promotionDto.getName());
        Promotion promotion = product.getPromotion();
        int promotionCount = promotion.getBuyQuantity() + promotion.getGetQuantity();
        return product.getPrice() * promotionDto.getFreeCount() * promotionCount;
    }

    private Product getProductByName(String name) {
        return productRepository.findProductByName(name)
                .orElseThrow(() -> new IllegalArgumentException());
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
