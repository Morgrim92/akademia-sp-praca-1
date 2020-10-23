package pl.morg.pracadomowa1.pracadomowa1;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private List<Product> productList = new ArrayList<>();

    private ProductRepository() {
        for (int i = 1; i < 6; i++) {
            Product product = new Product("Product " + i,
                    BigDecimal.valueOf((Math.random() * (300 - 50) + 1) + 50).setScale(2, RoundingMode.HALF_DOWN));
            addProduct(product);
        }
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public List<Product> getProducts() {
        return productList;
    }

    public BigDecimal getTotalPrice() {
        return productList.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
