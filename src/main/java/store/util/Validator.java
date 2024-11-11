package store.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static Map<String, Integer> validateProduct(String input) {
        Map<String, Integer> map = new HashMap<>();
        Matcher matcher = Pattern.compile("\\[(.+?)-(\\d+)]").matcher(input);
        while (matcher.find()) {
            String product = matcher.group(1);
            int quantity = isDigit(matcher.group(2));
            map.put(product, quantity);
        }

        return validateFormat(map);
    }

    private static int isDigit(String rawQuantity) {
        int quantity;
        try {
            quantity = Integer.parseInt(rawQuantity);
            isPositiveInt(quantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
        return quantity;
    }

    private static void isPositiveInt(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static Map<String, Integer> validateFormat(Map<String, Integer> map) {
        if (map.isEmpty()) {
            throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
        }
        return map;
    }

    public static boolean validateResponse(String input) {
        if (input.equalsIgnoreCase("Y")) {
            return true;
        } else if (input.equalsIgnoreCase("N")) {
            return false;
        }
        throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
    }
}
