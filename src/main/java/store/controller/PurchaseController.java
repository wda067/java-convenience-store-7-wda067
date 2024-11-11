package store.controller;

import store.service.PurchaseService;
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

       
    }
}
