package ma.youcode.myrhbackendapi.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UserRequest {
    private UUID id;
    private String fullName;
//    @NotNull(message = "email cannot be null")
//    @Email(message = "Please Enter a valid Email")
    private String email;
    private String phoneNumber;
}
