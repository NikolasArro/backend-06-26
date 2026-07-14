package ee.nikolas.backend0626.util;

import ee.nikolas.backend0626.dto.OrderRowDto;
import ee.nikolas.backend0626.entity.Product;
import ee.nikolas.backend0626.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CalculationUtilTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CalculationUtil calculationUtil;

    List<OrderRowDto> orderRowDtos = new ArrayList<>();

    @BeforeEach
    void setUp() {
    }

    @Test
    void givenOrderRowsEmpty_whenCalculatingSum_thenSumIs0() {
        double total = calculationUtil.calculateSum(orderRowDtos);

        assertEquals(0, total);
    }

    @Test
    void givenOrderRowPrice2Quantity2_whenCalculatingSum_thenSumIs1800() {
        mockProduct(9L, 900.0, 10);

        addProductToList(2, 9L);

        double total = calculationUtil.calculateSum(orderRowDtos);

        assertEquals(1800, total);
    }

    @Test
    void givenOrderRowTwoItems_whenCalculatingSum_thenSumIs120() {
        mockProduct(9L, 20.0, 10);
        mockProduct(8L, 30.0, 10);

        addProductToList(3, 9L);
        addProductToList(2, 8L);

        double total = calculationUtil.calculateSum(orderRowDtos);
        assertEquals(120, total);
    }

    private void addProductToList(int quantity, long productId) {
        OrderRowDto orderRowDto = new OrderRowDto(quantity, productId);
        orderRowDtos.add(orderRowDto);
    }

    private void mockProduct(long id, double price, int stock) {
        Product product = new Product();
        product.setId(id);
        product.setPrice(price);
        product.setStock(stock);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
    }
}