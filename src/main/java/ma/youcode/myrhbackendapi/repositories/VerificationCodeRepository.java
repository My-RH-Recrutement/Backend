package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {
    public Optional<VerificationCode> findVerificationCodeByCode(String code);
}
