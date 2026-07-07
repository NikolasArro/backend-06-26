package ee.nikolas.backend0626.service;

import ee.nikolas.backend0626.dto.OrderRowDto;
import ee.nikolas.backend0626.entity.Order;
import ee.nikolas.backend0626.entity.OrderRow;
import ee.nikolas.backend0626.entity.Person;
import ee.nikolas.backend0626.entity.Product;
import ee.nikolas.backend0626.repository.OrderRepository;
import ee.nikolas.backend0626.repository.PersonRepository;
import ee.nikolas.backend0626.util.CalculationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CalculationUtil calculationUtil;
    private final OrderCache orderCache;
    private final PersonRepository personRepository;

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

        if (SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
            throw new RuntimeException("Pole sisselogitud");
        }

        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Person person = personRepository.findById(id).orElseThrow();
        order.setPerson(person);
        order.setSum(calculationUtil.calculateSum(orderRowDtos));

        return orderRepository.save(order);
    }

    public Order getOrderDetails(Long id) throws ExecutionException {
        return orderCache.getOrder(id);
    }
}
