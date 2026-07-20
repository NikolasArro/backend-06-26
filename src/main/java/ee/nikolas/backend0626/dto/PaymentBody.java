package ee.nikolas.backend0626.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class PaymentBody {
    private String account_name;
    private String nonce;
    private String timestamp;
    private double amount;
    private String order_reference;
    private String customer_url;
    private String api_username;
}
