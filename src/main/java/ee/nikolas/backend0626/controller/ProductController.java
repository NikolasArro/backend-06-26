package ee.nikolas.backend0626.controller;

import ee.nikolas.backend0626.dto.PageableDto;
import ee.nikolas.backend0626.entity.Product;
import ee.nikolas.backend0626.repository.ProductRepository;
import ee.nikolas.backend0626.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductRepository productRepository;
    private final ValidationUtil validationUtil;

    @GetMapping("products/all")
    public List<Product> getProducts() {
        return productRepository.findAll(); // SELECT * FROM <tabel>
    }

    // localhost:8080/products?page=0&size=2&sort=id,asc&categoryId=1
    @GetMapping("products")
    public Page<Product> getAllProducts(Pageable pageable, @RequestParam(required = false) Long categoryId) {
        log.info("Võtan tooteid");
        if (categoryId == null) {
            return productRepository.findAll(pageable);
        }
        return productRepository.findAllByCategoryId(pageable, categoryId); // SELECT * FROM <tabel>
    }

    @PostMapping("products")
    public Product saveProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new RuntimeException("When adding product, don't give ID");
        }
        validationUtil.validateProduct(product);
        return productRepository.save(product); // INSERT INTO () VALUES <tabel>
    }

    @DeleteMapping("products/{id}")
    public List<Product> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return productRepository.findAll(); // SELECT * FROM <tabel>
    }

    @GetMapping("products/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @PutMapping("products")
    public Product updateProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            throw new RuntimeException("When updating product, you must give ID");
        }
        validationUtil.validateProduct(product);
        return productRepository.save(product);
    }

    // webSocket
    @MessageMapping("/products-update") // kui tellin ja kogus väheneb, siis pöördume siia
    @SendTo("/get-products") // avalehel subscriben
    public Page<Product> greeting(PageableDto pageableDto) throws Exception {
        return productRepository.findAll(PageRequest.of(pageableDto.getPage(), pageableDto.getSize(), Sort.by("stock").ascending()));
    }
}
