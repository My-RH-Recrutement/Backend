package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.JobOfferRequest;
import ma.youcode.myrhbackendapi.dto.responses.JobOfferResponse;
import ma.youcode.myrhbackendapi.entities.JobOffer;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.repositories.JobOfferRepository;
import ma.youcode.myrhbackendapi.repositories.RecruiterRepository;
import ma.youcode.myrhbackendapi.services.JobOfferService;
import ma.youcode.myrhbackendapi.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final RecruiterRepository recruiterRepository;
    private final ModelMapper mapper;

    @Override
    public List<JobOfferResponse> getAll() {
        List<JobOffer> jobOffers = jobOfferRepository.findAll();
        if (jobOffers.isEmpty()) throw new ResourceNotFoundException("No Job Offers Found");
        return jobOffers.stream().map(jobOffer -> mapper.map(jobOffer, JobOfferResponse.class)).toList();
    }

    @Override
    public Page<JobOfferResponse> getAll(Pageable pageable) {
        Page<JobOffer> jobOfferPage = jobOfferRepository.findAll(pageable);
        if (jobOfferPage.isEmpty()) throw new ResourceNotFoundException("No Job Offers Found");
        return jobOfferPage.map(jobOffer -> mapper.map(jobOffer, JobOfferResponse.class));
    }

    @Override
    public Optional<JobOfferResponse> find(UUID id) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Job Offer Found with ID: " + id));
        return Optional.of(mapper.map(jobOffer, JobOfferResponse.class));
    }

    @Override
    public Optional<JobOfferResponse> create(JobOfferRequest jobOfferRequest) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, jobOfferRequest.getRecruiter());
        Recruiter recruiter = recruiterRepository.findById(Utils.pareseStringToUUID(jobOfferRequest.getRecruiter()))
                .orElseThrow(() -> new ResourceNotFoundException("No Recruiter Found with ID: " + jobOfferRequest.getRecruiter()));
        JobOffer jobOffer = mapper.map(jobOfferRequest, JobOffer.class);
        jobOffer.setRecruiter(recruiter);
        JobOffer savedJobOffer = jobOfferRepository.save(jobOffer);
        return Optional.of(mapper.map(savedJobOffer, JobOfferResponse.class));
    }

    @Override
    public Optional<JobOfferResponse> update(JobOfferRequest jobOfferRequest, UUID id) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Job Offer Found with ID: " + id));
        jobOfferRequest.setId(String.valueOf(jobOffer.getId()));
        JobOffer jobOfferToUpdate = mapper.map(jobOfferRequest, JobOffer.class);
        JobOffer savedJobOffer = jobOfferRepository.save(jobOfferToUpdate);
        return Optional.of(mapper.map(savedJobOffer, JobOfferResponse.class));
    }

    @Override
    public boolean destroy(UUID id) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Job Offer Found with ID: " + id));
        jobOfferRepository.delete(jobOffer);
        return true;
    }
}
