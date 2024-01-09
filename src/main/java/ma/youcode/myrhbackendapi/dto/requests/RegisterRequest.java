package ma.youcode.myrhbackendapi.dto.requests;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull(message = "Full name cannot be null")
    @Size(min = 5, max = 50, message = "Full name must be min 5 to 50 max characters")
    private String fullName;
    @NotNull(message = "email cannot be null")
    @Email(message = "provide valid email")
    private String email;
    @NotNull(message = "password cannot be null")
    @Size(min = 8, max = 50, message = "password must be min 8 to 50 max characters")
    private String password;
    private String phoneNumber;
    @NotNull(message = "role cannot be null")
    private String role;
}
