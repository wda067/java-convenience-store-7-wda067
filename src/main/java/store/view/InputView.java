package store.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class InputView {

    public String inputProduct() {
        System.out.println("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return readLine();
    }

    public String confirmMembership() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return readLine();
    }

    public String confirmFreeItemAddition(String item, int count) {
        System.out.println("현재 " + item + "은(는) " + count + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        return readLine();
    }

    public String confirmPurchaseWithoutPromotion(String item, int count) {
        System.out.println("현재 " + item + " " + count + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        return readLine();
    }
}
