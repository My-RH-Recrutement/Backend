package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.responses.VerificationCodeResponse;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import ma.youcode.myrhbackendapi.entities.VerificationCode;
import ma.youcode.myrhbackendapi.exceptions.InvalidVerificationCodeException;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.exceptions.TokenExpirationException;
import ma.youcode.myrhbackendapi.repositories.VerificationCodeRepository;
import ma.youcode.myrhbackendapi.services.VerificationCodeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final ModelMapper mapper;

    @Override
    public Optional<VerificationCodeResponse> generateCode(String email) {
        String sub = email.substring(0, 4) + UUID.randomUUID();
        VerificationCodeResponse response = VerificationCodeResponse.builder()
                .code(sub.substring(0, 20))
                .expiration(LocalDateTime.now().plusMinutes(3))
                .build();
        return Optional.of(response);
    }

    @Override
    public Optional<VerificationCode> verifyCode(String code) {
        VerificationCode verificationCode = verificationCodeRepository.findVerificationCodeByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Validation Code"));
        if (LocalDateTime.now().isBefore(verificationCode.getExpiration().minusMinutes(3))) throw new InvalidVerificationCodeException("Verification Code is Invalid");
        if (LocalDateTime.now().isAfter(verificationCode.getExpiration())) throw new TokenExpirationException("Verification Code is Expired");
        return Optional.of(verificationCode);
    }

    @Override
    public Optional<VerificationCodeResponse> save(Recruiter recruiter, VerificationCodeResponse code) {
        VerificationCode verificationCode = mapper.map(code, VerificationCode.class);
        verificationCode.setRecruiter(recruiter);
        VerificationCode savedVerificationCode = verificationCodeRepository.save(verificationCode);
        return Optional.of(mapper.map(savedVerificationCode, VerificationCodeResponse.class));
    }
}
