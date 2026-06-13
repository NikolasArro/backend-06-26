package ee.nikolas.backend0626.util;

import ee.nikolas.backend0626.dto.OrderRowDto;
import ee.nikolas.backend0626.entity.Product;
import ee.nikolas.backend0626.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CalculationUtil {

    private final ProductRepository productRepository;

    public double calculateSum(List<OrderRowDto> orderRowDtos) {
        double sum = 0;

        for (OrderRowDto orderRowDto : orderRowDtos) {
            Product product = productRepository.findById(orderRowDto.productId()).orElseThrow();
            if (product.getStock() < orderRowDto.quantity()) {
                throw new RuntimeException("Not enough in stock");
            }
            sum += product.getPrice() * orderRowDto.quantity();
        }

        return sum;
    }
}
