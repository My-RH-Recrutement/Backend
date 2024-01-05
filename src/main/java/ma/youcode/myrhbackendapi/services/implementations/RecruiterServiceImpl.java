package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.RecruiterRequest;
import ma.youcode.myrhbackendapi.dto.requests.VerificationCodeRequest;
import ma.youcode.myrhbackendapi.dto.responses.RecruiterResponse;
import ma.youcode.myrhbackendapi.dto.responses.VerificationCodeResponse;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import ma.youcode.myrhbackendapi.entities.VerificationCode;
import ma.youcode.myrhbackendapi.exceptions.ResourceAlreadyExistException;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.repositories.RecruiterRepository;
import ma.youcode.myrhbackendapi.services.CloudinaryService;
import ma.youcode.myrhbackendapi.services.EmailService;
import ma.youcode.myrhbackendapi.services.RecruiterService;
import ma.youcode.myrhbackendapi.services.VerificationCodeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecruiterServiceImpl implements RecruiterService {

    private final RecruiterRepository recruiterRepository;
    private final CloudinaryService cloudinaryService;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;
    private final ModelMapper mapper;

    @Override
    public List<RecruiterResponse> getAll() {
        List<Recruiter> recruiters = recruiterRepository.findAll();
        if (recruiters.isEmpty())
            throw new ResourceNotFoundException("No Recruiters Found");
        return recruiters.stream().map(recruiter -> mapper.map(recruiter, RecruiterResponse.class)).toList();
    }

    @Override
    public Page<RecruiterResponse> getAll(Pageable pageable) {
        Page<Recruiter> recruiterPage = recruiterRepository.findAll(pageable);
        if (recruiterPage.isEmpty())
            throw new ResourceNotFoundException("No Recruiters Found");
        return recruiterPage.map(recruiter -> mapper.map(recruiter, RecruiterResponse.class));
    }

    @Override
    public Optional<RecruiterResponse> find(UUID id) {
        Optional<Recruiter> recruiter = recruiterRepository.findById(id);
        if (recruiter.isEmpty())
            throw new ResourceNotFoundException("No Recruiter Found with ID: " + id);
        return Optional.of(mapper.map(recruiter.get(), RecruiterResponse.class));
    }

    @Override
    public Optional<RecruiterResponse> create(RecruiterRequest recruiterRequest) {
        Optional<Recruiter> isRecruiterExist = recruiterRepository.findRecruiterByEmail(recruiterRequest.getEmail());
        if (isRecruiterExist.isPresent()) throw new ResourceAlreadyExistException("Recruiter already exist with this Email");
        // upload image to cloudinary
        String imageUrl = cloudinaryService.uploadFile(recruiterRequest.getImage());
        // create recruiter
        Recruiter recruiter = mapper.map(recruiterRequest, Recruiter.class);
        recruiter.setImage(imageUrl);
        Recruiter savedRecruiter = recruiterRepository.save(recruiter);
        // generate verification code and store it
        Optional<VerificationCodeResponse> code = verificationCodeService.generateCode(savedRecruiter.getEmail());
        assert code.isPresent();
        VerificationCodeResponse verificationCode = verificationCodeService.save(savedRecruiter, code.get())
                .orElseThrow(() -> new RuntimeException("Something went wrong"));
        // send verification code via email
        emailService.send(savedRecruiter.getEmail(), "MyRH account Verification Code", "Here is Your Verification code: `" + verificationCode.getCode() + "` it will last only for 3 minutes.");
        return Optional.of(mapper.map(savedRecruiter, RecruiterResponse.class));
    }

    @Override
    public Optional<RecruiterResponse> update(RecruiterRequest recruiterRequest, UUID id) {
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Recruiter Found with ID: " + id));

        recruiterRequest.setId(recruiter.getId());
        Recruiter mappedRecruiter = mapper.map(recruiterRequest, Recruiter.class);
        Recruiter updatedRecruiter = recruiterRepository.save(mappedRecruiter);
        return Optional.of(mapper.map(updatedRecruiter, RecruiterResponse.class));
    }

    @Override
    public boolean destroy(UUID id) {
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Recruiter Found with ID: " + id));
        recruiterRepository.delete(recruiter);
        return true;
    }

    @Override
    public Recruiter findRecruiterByEmail(String email) {
        return recruiterRepository.findRecruiterByEmail(email)
                .orElse(null);
    }

    @Override
    public Optional<RecruiterResponse> verifyAccount(VerificationCodeRequest request) {
        Optional<VerificationCode> code = verificationCodeService.verifyCode(request.getCode());
        assert code.isPresent();
        Recruiter recruiter = recruiterRepository.findById(code.get().getRecruiter().getId())
                .orElseThrow(() -> new ResourceNotFoundException("No Recruiter found with id: " + code.get().getRecruiter().getId()));
        recruiter.setVerified(true);
        recruiterRepository.save(recruiter);
        return Optional.of(mapper.map(recruiter, RecruiterResponse.class));
    }
}
