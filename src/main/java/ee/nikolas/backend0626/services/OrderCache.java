package ee.nikolas.backend0626.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.nikolas.backend0626.entity.Order;
import ee.nikolas.backend0626.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Component
@Log4j2
@RequiredArgsConstructor
public class OrderCache {
    private final OrderRepository orderRepository;

    private final LoadingCache<Long, Order> loadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofSeconds(10))
            .build(
                    new CacheLoader<>() {
                        @Override
                        @NullMarked
                        public Order load(Long id) {
                            log.info("Võtan andmebaasist");
                            return orderRepository.findById(id).orElseThrow();
                        }
                    });

    public Order getOrder(Long id) throws ExecutionException {
        log.info("Võtan tellimust...");
        return loadingCache.get(id);
    }
}
