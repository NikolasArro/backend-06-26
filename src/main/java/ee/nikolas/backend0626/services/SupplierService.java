package ee.nikolas.backend0626.services;

import ee.nikolas.backend0626.dto.Supplier1Product;
import ee.nikolas.backend0626.dto.Supplier2Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final RestTemplate restTemplate;

    public List<Supplier1Product> getSupplier1Products() {
        String url = "https://fakestoreapi.com/products";
        ResponseEntity<Supplier1Product[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Supplier1Product[].class);
        Supplier1Product[] body = response.getBody();

        if (body == null) {
            throw new RuntimeException("Error when fetching products from supplier");
        }
        return Arrays.stream(body)
                .filter(e -> e.getCategory().equals("electronics") && e.getRating().getRate() > 3.0)
                .toList();
    }

    public List<Supplier2Product> getSupplier2Products() {
        String url = "https://api.escuelajs.co/api/v1/products";
        ResponseEntity<Supplier2Product[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Supplier2Product[].class);
        Supplier2Product[] body = response.getBody();

        if (body == null) {
            throw new RuntimeException("Error when fetching products from supplier");
        }
        return Arrays.stream(body)
                .filter(e -> e.getPrice() < 500)
                .sorted(Comparator.comparing(Supplier2Product::getPrice))
                .toList();
    }
}
