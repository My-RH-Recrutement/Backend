package ma.youcode.myrhbackendapi.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    SUCCESS("success"),
    PENDING("pending"),
    FAILED("failed");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }
}
