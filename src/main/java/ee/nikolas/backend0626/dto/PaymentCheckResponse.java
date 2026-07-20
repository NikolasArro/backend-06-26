package ee.nikolas.backend0626.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class PaymentCheckResponse {
    public String account_name;
    public String order_reference;
    public String email;
    public String customer_ip;
    public String customer_url;
    public Date payment_created_at;
    public double initial_amount;
    public double standing_amount;
    public String payment_reference;
    public String payment_link;
    public ArrayList<PaymentMethod> payment_methods;
    public String api_username;
    public Object warnings;
    public Object processing_error;
    public String stan;
    public int fraud_score;
    public String payment_state;
    public String payment_method;
    public String trace_id;
    public Object cc_details;
    public Object ob_details;
    public Date transaction_time;
    public String sca_exemption;
    public Object detailed_fraud_check_results;
    public String acquiring_completed_at;
}
