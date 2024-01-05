package ma.youcode.myrhbackendapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @EmbeddedId
    private SeekerOfferId id;

    @Column(name = "motivationletter")
    private String motivationLetter;

    @ManyToOne
    @MapsId("jobSeekerId")
    @JoinColumn(name = "seeker_id")
    private JobSeeker jobSeeker;

    @ManyToOne
    @MapsId("jobOfferId")
    @JoinColumn(name = "offer_id")
    private JobOffer jobOffer;
}
