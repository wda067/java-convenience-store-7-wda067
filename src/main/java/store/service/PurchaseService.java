package store.service;

import java.time.LocalDate;
import java.util.Map;
import store.domain.Product;
import store.domain.Promotion;
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
