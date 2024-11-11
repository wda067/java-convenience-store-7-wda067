package store.controller;

import static camp.nextstep.edu.missionutils.DateTimes.now;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import store.dto.PromotionDto;
import store.service.PurchaseService;
import store.util.Validator;
import store.view.InputView;
import store.view.OutputView;

public class PurchaseController {

    private final PurchaseService purchaseService;
    private final InputView inputView;
    private final OutputView outputView;

    public PurchaseController(PurchaseService purchaseService, InputView inputView, OutputView outputView) {
        this.purchaseService = purchaseService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        //상품 목록 출력
        outputView.printWelcomeMessage();
        outputView.printProducts(purchaseService.getProducts());

        //상품과 수량 입력
        String inputProduct = inputView.inputProduct();
        Map<String, Integer> productInventory = Validator.validateProduct(inputProduct);
        purchaseService.initInventory(productInventory);

        //프로모션 적용
        LocalDate date = now().toLocalDate();
        List<PromotionDto> promotionDtos = purchaseService.applyApplicablePromotions(date);

        //재입력
        for (PromotionDto promotionDto : promotionDtos) {
            int availablePromotionQuantity = promotionDto.getAvailablePromotionQuantity();
            if (availablePromotionQuantity != 0) {
                String response = inputView.confirmFreeItemAddition(promotionDto.getName(), availablePromotionQuantity);
                boolean isConfirmed = Validator.validateResponse(response);
                if (isConfirmed) {
                    promotionDto.setFreeCount(promotionDto.getFreeCount() + 1);
                    productInventory.put(promotionDto.getName(), productInventory.get(promotionDto.getName()) + 1);
                }
            }
            int nonPromotionQuantity = promotionDto.getNonPromotionQuantity();
            if (nonPromotionQuantity != 0) {
                String response = inputView.confirmPurchaseWithoutPromotion(promotionDto.getName(),
                        nonPromotionQuantity);
                boolean isNotConfirmed = !Validator.validateResponse(response);
                if (isNotConfirmed) {
                    productInventory.put(promotionDto.getName(),
                            productInventory.get(promotionDto.getName()) - nonPromotionQuantity);
                }
            }
        }

    }
}
