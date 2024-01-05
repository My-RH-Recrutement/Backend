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
    private String password;
    private String Address;
    private String image;
    private boolean isVerified;

    @OneToMany(mappedBy = "recruiter", fetch = FetchType.LAZY)
    private List<JobOffer> jobOffers;
}
