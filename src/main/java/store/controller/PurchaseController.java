package store.controller;

import java.util.Map;
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

    }
}
