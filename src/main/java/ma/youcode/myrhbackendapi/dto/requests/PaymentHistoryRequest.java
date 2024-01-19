package ma.youcode.myrhbackendapi.dto.requests;

import lombok.Data;

@Data
public class PaymentHistoryRequest {
    private double amount;
    private String paymentMethod;
    private String transaction;
    private String paymentStatus;
    private String description;
    private String currency;
    private String receiptUrl;
}
