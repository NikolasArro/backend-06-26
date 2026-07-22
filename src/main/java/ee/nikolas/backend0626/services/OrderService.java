package ee.nikolas.backend0626.services;

import ee.nikolas.backend0626.dto.*;
import ee.nikolas.backend0626.entity.Order;
import ee.nikolas.backend0626.entity.OrderRow;
import ee.nikolas.backend0626.entity.Person;
import ee.nikolas.backend0626.entity.Product;
import ee.nikolas.backend0626.repository.OrderRepository;
import ee.nikolas.backend0626.repository.PersonRepository;
import ee.nikolas.backend0626.util.CalculationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CalculationUtil calculationUtil;
    private final OrderCache orderCache;
    private final PersonRepository personRepository;

    private final RestTemplate restTemplate;

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
//        order.setPaid();

        return orderRepository.save(order);
    }

    public Order getOrderDetails(Long id) throws ExecutionException {
        return orderCache.getOrder(id);
    }

    public List<ParcelMachine> getParcelMachines(String country) {
        String url = "https://www.omniva.ee/locations.json";
        ResponseEntity<ParcelMachine[]> response = restTemplate.exchange(url, HttpMethod.GET, null, ParcelMachine[].class);
        ParcelMachine[] body = response.getBody();

        if (body == null) {
            throw new RuntimeException("Error when fetching parcel machines");
        }
        return Arrays.stream(body)
                .filter(e -> e.getA0_name().equals(country))
                .toList();
    }

    public PaymentLink makePayment(Order order) {
        String url = "https://igw-demo.every-pay.com/api/v4/payments/oneoff";

        PaymentBody body = new PaymentBody();
        body.setAccount_name("EUR3D1");
        body.setNonce("ref-" + order.getId() + ZonedDateTime.now());
        body.setTimestamp(ZonedDateTime.now().toString());
        body.setAmount(order.getSum());
        body.setOrder_reference("ref-" + order.getId());
        body.setCustomer_url("https://www.postimees.ee/");
        body.setApi_username("e36eb40f5ec87fa2");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("e36eb40f5ec87fa2", "7b91a3b9e1b74524c2e9fc282f8ac8cd");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentBody> entity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<PaymentResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, PaymentResponse.class);
        PaymentResponse paymentBody = response.getBody();

        if (paymentBody == null) {
            throw new RuntimeException("Error when fetching parcel machines");
        }
        PaymentLink paymentLink = new PaymentLink();
        paymentLink.setLink(paymentBody.getPayment_link());
        return paymentLink;
    }

    public boolean checkPayment(String paymentReference) {
        String url = "https://igw-demo.every-pay.com/api/v4/payments/" + paymentReference + "?api_username=e36eb40f5ec87fa2&detailed=false";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("e36eb40f5ec87fa2", "7b91a3b9e1b74524c2e9fc282f8ac8cd");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentBody> entity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<PaymentCheckResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, PaymentCheckResponse.class);
        PaymentCheckResponse paymentBody = response.getBody();

        if (paymentBody == null) {
            throw new RuntimeException("Error when fetching payment status");
        }

        Order order = orderRepository.findById(Long.valueOf(paymentBody.getOrder_reference().replace("ref-", ""))).orElseThrow();
        if (paymentBody.getPayment_state().equals("settled")) {
            order.setPaid(true);
            orderRepository.save(order);
            return true;
        } else {
            order.setPaid(false);
            orderRepository.save(order);
            return false;
        }
    }
}
