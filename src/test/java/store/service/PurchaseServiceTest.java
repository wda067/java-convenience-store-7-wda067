package store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.dto.PromotionDto;
import store.dto.PurchaseDto;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

class PurchaseServiceTest {

    private PurchaseService purchaseService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository(new PromotionRepository());
        purchaseService = new PurchaseService(productRepository);
    }

    @Test
    void 상품과_수량을_장바구니에_저장한다() {
        //given
        Map<String, Integer> cart = new HashMap<>();
        cart.put("콜라", 5);
        cart.put("감자칩", 3);
        LocalDate date = DateTimes.now().toLocalDate();

        //when
        purchaseService.initCart(cart, date);

        //then
        Map<String, Integer> savedCart = purchaseService.getCart();
        assertEquals(5, savedCart.get("콜라"));
        assertEquals(3, savedCart.get("감자칩"));
    }

    @Test
    void 목록에_존재하지_않는_상품은_저장할_수_없다 () {
        //given
        Map<String, Integer> cart = new HashMap<>();
        cart.put("제로콜라", 5);
        cart.put("감자칩", 3);
        LocalDate date = DateTimes.now().toLocalDate();

        //expected
        assertThrows(IllegalArgumentException.class, () ->
                purchaseService.initCart(cart, date));
    }

    @Test
    void 재고_수량을_초과하면_예외를_발생한다 () {
        //given
        Map<String, Integer> cart = new HashMap<>();
        cart.put("콜라", 35);
        cart.put("감자칩", 3);
        LocalDate date = DateTimes.now().toLocalDate();

        //expected
        assertThrows(IllegalArgumentException.class, () ->
                purchaseService.initCart(cart, date));
    }

    @Test
    void 프로모션이_적용된_제품에_대한_PromotionDto_생성_테스트() {
        //given
        Map<String, Integer> cart = new HashMap<>();
        cart.put("콜라", 5);
        cart.put("감자칩", 3);
        LocalDate date = DateTimes.now().toLocalDate();
        purchaseService.initCart(cart, date);

        //when
        List<PromotionDto> promotionDtos = purchaseService.applyApplicablePromotions(date);

        //then
        assertEquals(2, promotionDtos.size());
        assertEquals("콜라", promotionDtos.get(0).getName());
        assertEquals("감자칩", promotionDtos.get(1).getName());
    }

    @Test
    void 프로모션이_적용된_제품에_대한_PromotionDto_미생성_테스트() {
        //given
        Map<String, Integer> cart = new HashMap<>();
        cart.put("비타민워터", 5);
        LocalDate date = DateTimes.now().toLocalDate();
        purchaseService.initCart(cart, date);

        //when
        List<PromotionDto> promotionDtos = purchaseService.applyApplicablePromotions(date);

        //then
        assertTrue(promotionDtos.isEmpty());
    }

    @Test
    void 구매_금액_계산_및_프로모션과_멤버십_할인_적용_테스트() {
        //given
        Map<String, Integer> cart = new HashMap<>();
        cart.put("콜라", 3);
        cart.put("에너지바", 5);
        LocalDate date = DateTimes.now().toLocalDate();
        purchaseService.initCart(cart, date);

        boolean hasMembership = true;
        List<PromotionDto> promotionDtos = purchaseService.applyApplicablePromotions(date);

        //when
        PurchaseDto purchaseDto = purchaseService.calculatePurchase(promotionDtos, hasMembership);

        //then
        assertEquals(8, purchaseDto.getTotalCount());
        assertEquals(13_000, purchaseDto.getTotalAmount());
        assertEquals("-1,000", purchaseDto.getPromotionDiscount());
        assertEquals("-3,000", purchaseDto.getMembershipDiscount());
        assertEquals(9_000, purchaseDto.getFinalAmount());
    }

    @Test
    void 모든_할인_미적용_구매_금액_계산_테스트() {
        //given
        Map<String, Integer> cart = new HashMap<>();
        cart.put("물", 7);
        LocalDate date = DateTimes.now().toLocalDate();
        purchaseService.initCart(cart, date);

        boolean hasMembership = false;
        List<PromotionDto> promotionDtos = purchaseService.applyApplicablePromotions(date);

        //when
        PurchaseDto purchaseDto = purchaseService.calculatePurchase(promotionDtos, hasMembership);

        //then
        assertEquals(7, purchaseDto.getTotalCount());
        assertEquals(3_500, purchaseDto.getTotalAmount());
        assertEquals("-0", purchaseDto.getPromotionDiscount());
        assertEquals("-0", purchaseDto.getMembershipDiscount());
        assertEquals(3_500, purchaseDto.getFinalAmount());
    }

    @Test
    void 멤버십할인_적용_구매_금액_계산_테스트() {
        //given
        Map<String, Integer> cart = new HashMap<>();
        cart.put("물", 7);
        LocalDate date = DateTimes.now().toLocalDate();
        purchaseService.initCart(cart, date);

        boolean hasMembership = true;
        List<PromotionDto> promotionDtos = purchaseService.applyApplicablePromotions(date);

        //when
        PurchaseDto purchaseDto = purchaseService.calculatePurchase(promotionDtos, hasMembership);

        //then
        assertEquals(7, purchaseDto.getTotalCount());
        assertEquals(3_500, purchaseDto.getTotalAmount());
        assertEquals("-0", purchaseDto.getPromotionDiscount());
        assertEquals("-1,050", purchaseDto.getMembershipDiscount());
        assertEquals(2_450, purchaseDto.getFinalAmount());
    }
}