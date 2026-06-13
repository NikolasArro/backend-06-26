package ee.nikolas.backend0626.controller;

import ee.nikolas.backend0626.dto.OrderRowDto;
import ee.nikolas.backend0626.entity.Order;
import ee.nikolas.backend0626.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("orders")
    public Order saveOrder(@RequestBody List<OrderRowDto> orderRowDtos) {
        return orderService.saveOrder(orderRowDtos);
    }
 }
