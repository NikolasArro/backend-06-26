package ee.nikolas.backend0626.repository;

import ee.nikolas.backend0626.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCreatedAfterAndCreatedBefore(Date after, Date before);
}
