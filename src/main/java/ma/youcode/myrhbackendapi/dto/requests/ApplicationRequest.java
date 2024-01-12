package ma.youcode.myrhbackendapi.dto.requests;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;
import ma.youcode.myrhbackendapi.enums.StatusPostulation;

@Data
public class ApplicationRequest {
    private SeekerOfferId id;

    private String motivationLetter;

    private JobSeekerRequest jobSeeker;

    private JobOfferRequest jobOffer;

    private boolean isOnline;

    @Enumerated(EnumType.STRING)
    private StatusPostulation statusPostulation;
}
