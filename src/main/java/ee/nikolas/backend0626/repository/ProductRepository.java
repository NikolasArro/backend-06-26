package ee.nikolas.backend0626.repository;

import ee.nikolas.backend0626.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategoryId(Pageable pageable, Long categoryId);

    List<Product> findByCategoryIdAndStockGreaterThanAndActiveTrue(Pageable pageable, Long categoryId, Integer stock);
}
