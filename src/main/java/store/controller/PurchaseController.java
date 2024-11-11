package store.controller;

import static camp.nextstep.edu.missionutils.DateTimes.now;
import static store.util.Validator.validateResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import store.dto.PromotionDto;
import store.dto.PurchaseDto;
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
        while (true) {
            LocalDate date = now().toLocalDate();
            displayWelcomeAndProducts();
            initCart(date);
            List<PromotionDto> promotionDtos = applyPromotions(date);
            PurchaseDto purchaseDto = calculatePurchase(promotionDtos);
            displayReceipt(promotionDtos, purchaseDto);

            if (!isAdditionalPurchase(date)) {
                break;
            }
            System.out.println();
        }
    }

    private void displayWelcomeAndProducts() {
        outputView.printWelcomeMessage();
        outputView.printProducts(purchaseService.getProducts());
    }

    private void initCart(LocalDate date) {
        while (true) {
            try {
                String inputProduct = inputView.inputProduct();
                Map<String, Integer> cart = Validator.validateProduct(inputProduct);
                purchaseService.initCart(cart, date);
                return;
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    private List<PromotionDto> applyPromotions(LocalDate date) {
        List<PromotionDto> promotionDtos = purchaseService.applyApplicablePromotions(date);

        for (PromotionDto promotionDto : promotionDtos) {
            handlePromotionInput(purchaseService.getCart(), promotionDto);
        }

        return promotionDtos;
    }

    private void handlePromotionInput(Map<String, Integer> cart, PromotionDto promotionDto) {
        int availablePromotionQuantity = promotionDto.getAvailablePromotionQuantity();
        if (availablePromotionQuantity > 0) {
            handleFreeProductAddition(cart, promotionDto);
        }
        int nonPromotionQuantity = promotionDto.getNonPromotionQuantity();
        if (nonPromotionQuantity > 0) {
            handlePurchaseWithoutPromotion(cart, promotionDto);
        }
    }

    private void handleFreeProductAddition(Map<String, Integer> cart, PromotionDto promotionDto) {
        int availablePromotionQuantity = promotionDto.getAvailablePromotionQuantity();
        while (true) {
            try {
                String response = inputView.confirmFreeItemAddition(promotionDto.getName(), availablePromotionQuantity);
                if (validateResponse(response)) {
                    promotionDto.setFreeCount(promotionDto.getFreeCount() + 1);
                    cart.put(promotionDto.getName(), cart.get(promotionDto.getName()) + 1);
                }
                break;
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    private void handlePurchaseWithoutPromotion(Map<String, Integer> productInventory, PromotionDto promotionDto) {
        int nonPromotionQuantity = promotionDto.getNonPromotionQuantity();
        while (true) {
            try {
                String response = inputView.confirmPurchaseWithoutPromotion(promotionDto.getName(),
                        nonPromotionQuantity);
                boolean isNotConfirmed = !validateResponse(response);
                if (isNotConfirmed) {
                    productInventory.put(promotionDto.getName(),
                            productInventory.get(promotionDto.getName()) - nonPromotionQuantity);
                }
                break;
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    private PurchaseDto calculatePurchase(List<PromotionDto> promotionDtos) {
        boolean hasMembership;
        while (true) {
            try {
                hasMembership = validateResponse(inputView.confirmMembership());
                break;
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
        return purchaseService.calculatePurchase(promotionDtos, hasMembership);
    }

    private void displayReceipt(List<PromotionDto> promotionDtos, PurchaseDto purchaseDto) {
        outputView.printPurchasedProducts(purchaseService.getProductDtos());
        outputView.printFreeProducts(promotionDtos);
        outputView.printPaymentSummary(purchaseDto);
    }

    private boolean isAdditionalPurchase(LocalDate date) {
        while (true) {
            try {
                boolean isAdditionalPurchase = validateResponse(inputView.confirmAdditionalPurchase());
                purchaseService.updateQuantity(date);
                return isAdditionalPurchase;
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }
}
