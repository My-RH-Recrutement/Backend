package ma.youcode.myrhbackendapi.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubscriptionRequest {
    @NotEmpty(message = "Subscription Status is required")
    @NotNull(message = "Subscription Status cannot be empty")
    private String subscriptionStatus;
    private String cancellationReason;
    @NotNull(message = "Recruiter ID cannot be empty")
    @NotEmpty(message = "Recruiter ID is required")
    private String recruiter;
    @NotNull(message = "Pack ID cannot be null")
    @NotEmpty(message = "Pack ID is required")
    private String pack;
    private LocalDateTime createAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
