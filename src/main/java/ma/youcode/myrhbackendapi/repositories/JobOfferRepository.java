package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobOfferRepository extends JpaRepository<JobOffer, UUID> {
}
