package store.view;

import static camp.nextstep.edu.missionutils.Console.readLine;
import static store.enums.Message.CONFIRM_ADDITIONAL_PURCHASE;
import static store.enums.Message.CONFIRM_FREE_ITEM_ADDITION;
import static store.enums.Message.CONFIRM_MEMBERSHIP;
import static store.enums.Message.CONFIRM_PURCHASE_WITHOUT_PROMOTION;
import static store.enums.Message.INPUT_PRODUCT;

public class InputView {

    public String inputProduct() {
        System.out.println(INPUT_PRODUCT.getMessage());
        return readLine();
    }

    public String confirmMembership() {
        System.out.println(CONFIRM_MEMBERSHIP.getMessage());
        return readLine();
    }

    public String confirmFreeItemAddition(String product, int count) {
        System.out.printf(CONFIRM_FREE_ITEM_ADDITION.getMessage(), product, count);
        return readLine();
    }

    public String confirmPurchaseWithoutPromotion(String product, int count) {
        System.out.printf(CONFIRM_PURCHASE_WITHOUT_PROMOTION.getMessage(), product, count);
        return readLine();
    }

    public String confirmAdditionalPurchase() {
        System.out.println(CONFIRM_ADDITIONAL_PURCHASE.getMessage());
        return readLine();
    }
}
