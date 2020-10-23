package pl.morg.pracadomowa1.pracadomowa1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Profile("plus")
public class ShopPlusService {

    @Value("${shop.vat.rate}")
    private double vat;
    private ProductRepository productRepository;

    @Autowired
    public ShopPlusService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        productRepository.getProducts()
                .forEach(product -> System.out.println(product.getName()
                        + " - " + product.getPrice()
                        + "$ + VAT: "
                        + calculateVat(product.getPrice())
                        + "$"));
        BigDecimal totalPrice = productRepository.getTotalPrice();
        System.out.println("Total price: " + totalPrice + "$ + VAT: " + calculateVat(totalPrice) + "$");
    }

    private String calculateVat(BigDecimal totalPrice) {
        return totalPrice
                .multiply(BigDecimal.valueOf(vat))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.CEILING)
                .toString();
    }
}
