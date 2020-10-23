package pl.morg.pracadomowa1.pracadomowa1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Profile("start")
public class ShopStartService {
    private final ProductRepository productRepository;

    @Autowired
    public ShopStartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        productRepository.getProducts().forEach(System.out::println);
        System.out.println("Total price: " + productRepository.getTotalPrice() + "$");
    }
}
