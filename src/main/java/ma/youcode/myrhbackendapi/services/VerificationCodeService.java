package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.responses.VerificationCodeResponse;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import ma.youcode.myrhbackendapi.entities.VerificationCode;

import java.util.Optional;

public interface VerificationCodeService {
    public Optional<VerificationCodeResponse> generateCode(String email);
    public Optional<VerificationCode> verifyCode(String code);
    public Optional<VerificationCodeResponse> save(Recruiter recruiter, VerificationCodeResponse verificationCode);
}
