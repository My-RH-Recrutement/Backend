package ma.youcode.myrhbackendapi.dto.responses;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;
import ma.youcode.myrhbackendapi.enums.StatusPostulation;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private SeekerOfferId id;

    private String motivationLetter;

    private JobSeekerResponse jobSeeker;

    private JobOfferResponse jobOffer;

    @Enumerated(EnumType.STRING)
    private StatusPostulation statusPostulation;
}
