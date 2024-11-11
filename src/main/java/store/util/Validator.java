package store.util;

import static store.enums.Constants.ONE_VALUE;
import static store.enums.Constants.TWO_VALUE;
import static store.enums.Constants.ZERO_VALUE;
import static store.enums.ExceptionMessage.INVALID_FORMAT;
import static store.enums.ExceptionMessage.INVALID_RESPONSE;
import static store.enums.Message.NO;
import static store.enums.Message.YES;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String productRegex = "\\[(.+?)-(\\d+)]";

    public static Map<String, Integer> validateProduct(String input) {
        Map<String, Integer> map = new HashMap<>();
        Matcher matcher = Pattern.compile(productRegex).matcher(input);
        while (matcher.find()) {
            String product = matcher.group(ONE_VALUE.getValue());
            int quantity = isDigit(matcher.group(TWO_VALUE.getValue()));
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
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }
        return quantity;
    }

    private static void isPositiveInt(int quantity) {
        if (quantity <= ZERO_VALUE.getValue()) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }
    }

    private static Map<String, Integer> validateFormat(Map<String, Integer> map) {
        if (map.isEmpty()) {
            throw new IllegalArgumentException(INVALID_RESPONSE.getMessage());
        }
        return map;
    }

    public static boolean validateResponse(String input) {
        if (input.equalsIgnoreCase(YES.getMessage())) {
            return true;
        } else if (input.equalsIgnoreCase(NO.getMessage())) {
            return false;
        }
        throw new IllegalArgumentException(INVALID_RESPONSE.getMessage());
    }
}
