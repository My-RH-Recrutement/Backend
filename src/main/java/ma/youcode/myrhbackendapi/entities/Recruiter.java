package ma.youcode.myrhbackendapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recruiters")
public class Recruiter extends User {
    private String Address;
    private String image;

    @OneToMany(mappedBy = "recruiter", fetch = FetchType.LAZY)
    private List<JobOffer> jobOffers;
}
