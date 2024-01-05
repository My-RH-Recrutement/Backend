package ma.youcode.myrhbackendapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String code;
    private LocalDateTime expiration;

    @OneToOne
    private Recruiter recruiter;
}
