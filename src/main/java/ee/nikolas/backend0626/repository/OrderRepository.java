package ee.nikolas.backend0626.repository;

import ee.nikolas.backend0626.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
