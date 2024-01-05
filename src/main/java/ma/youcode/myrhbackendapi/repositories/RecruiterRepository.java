package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RecruiterRepository extends JpaRepository<Recruiter, UUID> {
    public Optional<Recruiter> findRecruiterByEmail(String email);
}
