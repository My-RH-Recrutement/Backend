package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.requests.ApplicationRequest;
import ma.youcode.myrhbackendapi.dto.responses.ApplicationResponse;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;
import ma.youcode.myrhbackendapi.enums.StatusPostulation;
import ma.youcode.myrhbackendapi.interfaces.CrudInterface;

import java.util.List;
import java.util.UUID;

public interface ApplicationService extends CrudInterface<ApplicationResponse, ApplicationRequest, SeekerOfferId> {
    List<ApplicationResponse> getPostulationByIdJobSeeker(UUID idJobSeeker);

    List<ApplicationResponse> getPostulationByIdJobSeekerAndStatusPostulation(UUID idJobSeeker, StatusPostulation statusPostulation);
}
