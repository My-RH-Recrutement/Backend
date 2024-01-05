package ma.youcode.myrhbackendapi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agents")
public class Agent extends User {
    private String password;
}
