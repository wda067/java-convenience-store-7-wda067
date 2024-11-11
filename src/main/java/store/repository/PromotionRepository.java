package store.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.domain.Promotion;

public class PromotionRepository {

    private static final String FILE_PATH = "src/main/resources/promotions.md";
    private static final String COMMA = ",";
    private final List<Promotion> promotions;

    public PromotionRepository() {
        promotions = new ArrayList<>();
        loadProducts();
    }

    private void loadProducts() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(COMMA);
                String name = data[0].trim();
                int buyQuantity = Integer.parseInt(data[1].trim());
                int getQuantity = Integer.parseInt(data[2].trim());
                LocalDate startDate = LocalDate.parse(data[3].trim());
                LocalDate endDate = LocalDate.parse(data[4].trim());

                Promotion promotion = new Promotion(name, buyQuantity, getQuantity, startDate, endDate);
                promotions.add(promotion);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Promotion> findPromotionByName(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(name))
                .findFirst();
    }
}
