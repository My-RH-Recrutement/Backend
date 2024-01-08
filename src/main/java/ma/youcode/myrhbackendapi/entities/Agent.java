package ma.youcode.myrhbackendapi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "agents")
public class Agent extends User {
}
