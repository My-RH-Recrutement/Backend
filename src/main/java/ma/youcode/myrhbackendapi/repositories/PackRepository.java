package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PackRepository extends JpaRepository<Pack, UUID> {
}
