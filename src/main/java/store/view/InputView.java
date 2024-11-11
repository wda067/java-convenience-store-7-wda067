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
}
