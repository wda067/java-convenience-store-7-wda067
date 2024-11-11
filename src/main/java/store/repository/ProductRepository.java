package store.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.domain.Product;
import store.domain.Promotion;

public class ProductRepository {

    private static final String FILE_PATH = "src/main/resources/products.md";
    private static final String COMMA = ",";
    private final List<Product> products;
    private final PromotionRepository promotionRepository;

    public ProductRepository(PromotionRepository promotionRepository) {
        this.products = new ArrayList<>();
        this.promotionRepository = promotionRepository;
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
                int price = Integer.parseInt(data[1].trim());
                int quantity = Integer.parseInt(data[2].trim());
                Promotion promotion = promotionRepository.findPromotionByName(data[3].trim())
                        .orElse(null);

                Optional<Product> optionalProduct = findProductByName(name);
                if (optionalProduct.isEmpty()) {
                    Product product = new Product(name, price, quantity, promotion);
                    products.add(product);
                    continue;
                }

                Product product = optionalProduct.get();
                if (promotion == null) {
                    product.setQuantity(quantity);
                    continue;
                }
                product.setPromotionQuantity(quantity);
                product.setPromotion(promotion);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Product> findProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    public List<Product> findAllProducts() {
        return products;
    }
}
