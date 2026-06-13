package ee.nikolas.backend0626.repository;

import ee.nikolas.backend0626.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
