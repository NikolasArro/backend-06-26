package ee.nikolas.backend0626.service;

import ee.nikolas.backend0626.dto.OrderRowDto;
import ee.nikolas.backend0626.entity.Order;
import ee.nikolas.backend0626.entity.OrderRow;
import ee.nikolas.backend0626.entity.Person;
import ee.nikolas.backend0626.entity.Product;
import ee.nikolas.backend0626.repository.OrderRepository;
import ee.nikolas.backend0626.util.CalculationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CalculationUtil calculationUtil;

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order saveOrder(List<OrderRowDto> orderRowDtos) {
        Order order = new Order();
        order.setCreated(new Date());
        List<OrderRow> orderRows = new ArrayList<>();
        for (OrderRowDto orderRowDto : orderRowDtos) {
            OrderRow orderRow = new OrderRow();
            orderRow.setQuantity(orderRowDto.quantity());
            Product product = new Product();
            product.setId(orderRowDto.productId());
            orderRow.setProduct(product);

            orderRows.add(orderRow);
        }

        order.setRows(orderRows);
        Person person = new Person(); // TODO: Hiljem autentimise kaudu
        person.setId(1L); // TODO: Hiljem autentimise kaudu
        order.setPerson(person);
        order.setSum(calculationUtil.calculateSum(orderRowDtos));

        return orderRepository.save(order);
    }
}
