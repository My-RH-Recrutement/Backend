package ma.youcode.myrhbackendapi.dto.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AgentRequest extends UserRequest {
    private String password;
}
