package store.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

class PromotionTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository(new PromotionRepository());
    }

    @Test
    void 날짜가_탄산_프로모션_기간에_포함된다() {
        //given
        LocalDate date = LocalDate.of(2024, 11, 10);
        Product coke = productRepository.findProductByName("콜라")
                .orElseThrow();
        Promotion promotion = coke.getPromotion();

        //expected
        LocalDate startDate = promotion.getStartDate();
        LocalDate endDate = promotion.getEndDate();
        assertTrue((date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate)));
    }

    @Test
    void 날짜가_탄산_프로모션_기간에_포함되지_않는다() {
        //given
        LocalDate date = LocalDate.of(2023, 1, 1);
        Product cider = productRepository.findProductByName("사이다")
                .orElseThrow();
        Promotion promotion = cider.getPromotion();

        //expected
        LocalDate startDate = promotion.getStartDate();
        LocalDate endDate = promotion.getEndDate();
        assertFalse((date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate)));
    }

    @Test
    void 날짜가_MD_프로모션_기간에_포함된다() {
        //given
        LocalDate date = LocalDate.of(2024, 12, 10);
        Product orangeJuice = productRepository.findProductByName("오렌지주스")
                .orElseThrow();
        Promotion promotion = orangeJuice.getPromotion();

        //expected
        LocalDate startDate = promotion.getStartDate();
        LocalDate endDate = promotion.getEndDate();
        assertTrue((date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate)));
    }

    @Test
    void 날짜가_MD_프로모션_기간에_포함되지_않는다() {
        //given
        LocalDate date = LocalDate.of(2023, 12, 1);
        Product chocolateBar = productRepository.findProductByName("초코바")
                .orElseThrow();
        Promotion promotion = chocolateBar.getPromotion();

        //expected
        LocalDate startDate = promotion.getStartDate();
        LocalDate endDate = promotion.getEndDate();
        assertFalse((date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate)));
    }

    @Test
    void 날짜가_반짝할인_프로모션_기간에_포함된다() {
        //given
        LocalDate date = LocalDate.of(2024, 11, 15);
        Product potatoChips = productRepository.findProductByName("감자칩")
                .orElseThrow();
        Promotion promotion = potatoChips.getPromotion();

        //expected
        LocalDate startDate = promotion.getStartDate();
        LocalDate endDate = promotion.getEndDate();
        assertTrue((date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate)));
    }

    @Test
    void 날짜가_반짝할인_프로모션_기간에_포함되지_않는다() {
        //given
        LocalDate date = LocalDate.of(2024, 12, 1);
        Product potatoChips = productRepository.findProductByName("감자칩")
                .orElseThrow();
        Promotion promotion = potatoChips.getPromotion();

        //expected
        LocalDate startDate = promotion.getStartDate();
        LocalDate endDate = promotion.getEndDate();
        assertFalse((date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate)));
    }
}