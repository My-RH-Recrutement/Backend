package ma.youcode.myrhbackendapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;
import ma.youcode.myrhbackendapi.enums.StatusPostulation;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @EmbeddedId
    private SeekerOfferId id;

    @Column(name = "is0nline")
    private boolean isOnline;
    /*true == Online && false == Offline*/

    @Column(name = "motivationletter")
    private String motivationLetter;

    @Column(name = "statusPostulation")
    @Enumerated(EnumType.STRING)
    private StatusPostulation statusPostulation;

    @ManyToOne
    @MapsId("jobSeekerId")
    @JoinColumn(name = "seeker_id")
    private JobSeeker jobSeeker;

    @ManyToOne
    @MapsId("jobOfferId")
    @JoinColumn(name = "offer_id")
    private JobOffer jobOffer;
}
