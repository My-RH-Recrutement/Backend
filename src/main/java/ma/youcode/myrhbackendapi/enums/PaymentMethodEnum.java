package ma.youcode.myrhbackendapi.enums;

import lombok.Getter;

@Getter
public enum PaymentMethodEnum {
    CREDIT_CARD("Credit Card"),
    STRIPE("stripe"),
    PAYPAL("paypal"),
    BANK_TRANSFER("bank transfer");

    private final String methodName;

    PaymentMethodEnum(String methodName) {
        this.methodName = methodName;
    }
}
