package ma.youcode.myrhbackendapi.dto.responses;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentResponse extends UserResponse {
    private String password;
}
