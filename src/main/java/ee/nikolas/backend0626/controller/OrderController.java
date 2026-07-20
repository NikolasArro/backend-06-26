package ee.nikolas.backend0626.controller;

import ee.nikolas.backend0626.dto.OrderRowDto;
import ee.nikolas.backend0626.dto.ParcelMachine;
import ee.nikolas.backend0626.dto.PaymentLink;
import ee.nikolas.backend0626.entity.Order;
import ee.nikolas.backend0626.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("orders/{id}")
    public Order getOrderDetails(@PathVariable Long id) throws ExecutionException {
        return orderService.getOrderDetails(id);
    }

    @PostMapping("orders")
    public PaymentLink saveOrder(@RequestBody List<OrderRowDto> orderRowDtos) {
        Order order = orderService.saveOrder(orderRowDtos); // miks salvestada enne? Maksesse saadame ID. Kui raha läheb maha, aga anmebaasi ei salvestu, siis on jälg olemas
        return orderService.makePayment(order);
    }

    @GetMapping("parcelmachines")
    public List<ParcelMachine> getParcelMachines(@RequestParam String country) {
        return orderService.getParcelMachines(country);
    }

    @GetMapping("check-payment")
    public boolean checkPayment(@RequestParam String paymentReference) {
        return orderService.checkPayment(paymentReference);
    }


 }
