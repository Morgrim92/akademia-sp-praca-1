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
@Profile("pro")
public class ShopProService {

    @Value("${shop.vat.rate}")
    private double vat;
    @Value("${shop.discount.rate}")
    private double discount;
    private ProductRepository productRepository;

    @Autowired
    public ShopProService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        productRepository.getProducts()
                .forEach(product -> System.out.println(product.getName()
                        + " - "
                        + product.getPrice().subtract(calculateDiscount(product.getPrice()))
                        + "$ + VAT: "
                        + calculateVat(product.getPrice().subtract(calculateDiscount(product.getPrice())) )
                        + "$"));
        BigDecimal totalPrice = productRepository.getTotalPrice();
        System.out.println("Total price after discount: "
                + totalPrice.subtract(calculateDiscount(totalPrice))
                + "$ + VAT: " + calculateVat(totalPrice)
                + "$");
    }

    private BigDecimal calculateVat(BigDecimal price) {
        return price
                .multiply(BigDecimal.valueOf(vat))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.CEILING);
    }

    private BigDecimal calculateDiscount(BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(discount))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.CEILING);
    }
}
