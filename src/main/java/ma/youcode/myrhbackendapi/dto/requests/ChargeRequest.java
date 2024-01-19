package ma.youcode.myrhbackendapi.dto.requests;

import lombok.Data;
import ma.youcode.myrhbackendapi.enums.Currency;

@Data
public class ChargeRequest {
    private String description;

    private Long amount;

    private Currency currency;

    private String token;
}
