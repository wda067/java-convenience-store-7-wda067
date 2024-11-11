package store.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static Map<String, Integer> validateProduct(String input) {
        Map<String, Integer> map = new HashMap<>();
        Pattern pattern = Pattern.compile("\\[(.+?)-(\\d+)]");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String product = matcher.group(1);
            int quantity = Integer.parseInt(matcher.group(2));
            map.put(product, quantity);
        }

        return map;
    }

    public static boolean validateResponse(String input) {
        if (input.equalsIgnoreCase("Y")) {
            return true;
        } else if (input.equalsIgnoreCase("N")) {
            return false;
        }
        throw new IllegalArgumentException();
    }
}
