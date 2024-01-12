package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.Application;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;
import ma.youcode.myrhbackendapi.enums.StatusPostulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, SeekerOfferId> {
    List<Application> findAllByJobSeekerId(UUID idJobSeeker);

    List<Application> getPostulationByIdJobSeekerIdAndStatusPostulation(UUID idJobSeeker, StatusPostulation statusPostulation);
}
