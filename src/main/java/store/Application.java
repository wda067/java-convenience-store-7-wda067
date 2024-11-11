package store;

import store.controller.PurchaseController;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.PurchaseService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository(new PromotionRepository());
        PurchaseController purchaseController = new PurchaseController(
                new PurchaseService(productRepository),
                new InputView(),
                new OutputView()
        );

        purchaseController.run();
    }
}
