package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.RecruiterRequest;
import ma.youcode.myrhbackendapi.dto.requests.RegisterRequest;
import ma.youcode.myrhbackendapi.dto.requests.UserRequest;
import ma.youcode.myrhbackendapi.dto.requests.VerificationCodeRequest;
import ma.youcode.myrhbackendapi.dto.responses.AuthResponse;
import ma.youcode.myrhbackendapi.dto.responses.RecruiterResponse;
import ma.youcode.myrhbackendapi.dto.responses.UserResponse;
import ma.youcode.myrhbackendapi.dto.responses.VerificationCodeResponse;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import ma.youcode.myrhbackendapi.entities.User;
import ma.youcode.myrhbackendapi.entities.VerificationCode;
import ma.youcode.myrhbackendapi.enums.Access;
import ma.youcode.myrhbackendapi.exceptions.ResourceAlreadyExistException;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.exceptions.SomethingWentWrongException;
import ma.youcode.myrhbackendapi.repositories.UserRepository;
import ma.youcode.myrhbackendapi.security.jwt.JwtService;
import ma.youcode.myrhbackendapi.services.AuthService;
import ma.youcode.myrhbackendapi.services.EmailService;
import ma.youcode.myrhbackendapi.services.RecruiterService;
import ma.youcode.myrhbackendapi.services.VerificationCodeService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;
    private final RecruiterService recruiterService;

    /**
     *
     * @param userRequest
     * @return
     */
    @Override
    public Optional<AuthResponse> login(UserRequest userRequest) {
        Authentication authentication = authenticateUser(userRequest.getEmail(), userRequest.getPassword());
        User user  = userRepository.findUserByEmail(userRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("No User found with Email:" + userRequest.getEmail()));
        String verificationCode = generateAndSaveVerificationCode(user);
        sendVerificationCodeViaEmail(user.getEmail(), verificationCode);
        String jwt = jwtService.generateToken(authentication, user);
        return Optional.of(
                AuthResponse.builder()
                        .token(jwt)
                        .build()
        );
    }

    /**
     *
     * @param userRequest
     * @return
     */
    @Override
    public Optional<AuthResponse> register(RegisterRequest userRequest) {
        Optional<User> user = userRepository.findUserByEmail(userRequest.getEmail());
        if (user.isPresent()) throw new ResourceAlreadyExistException("User already exist with this email: " + userRequest.getEmail());

        String password = userRequest.getPassword();

        userRequest.setPassword(passwordEncoder.encode(password));
        User userToSave = mapper.map(userRequest, User.class);

        if (userRequest.getRole().equals("RECRUITER")) userToSave = registerRecruiter(userRequest);
        else userToSave = userRepository.save(userToSave);

        String verificationCode = generateAndSaveVerificationCode(userToSave);
        sendVerificationCodeViaEmail(userToSave.getEmail(), verificationCode);

        Authentication authentication = authenticateUser(userRequest.getEmail(), password);
        String jwt = jwtService.generateToken(authentication, userToSave);
        return Optional.of(
                AuthResponse.builder()
                        .token(jwt)
                        .build()
        );
    }

    public Authentication authenticateUser(String username, String password) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        }catch (BadCredentialsException exception) {
            throw new BadCredentialsException("Invalid Credentials!, please check your email or password: " + exception.getMessage());
        }
    }

    @Override
    public Optional<UserResponse> verifyAccount(VerificationCodeRequest request) {
        Optional<VerificationCode> code = verificationCodeService.verifyCode(request.getCode());
        assert code.isPresent();
        User user = userRepository.findById(code.get().getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("No Recruiter found with id: " + code.get().getUser().getId()));
        user.setVerified(true);
        userRepository.save(user);
        return Optional.of(mapper.map(user, UserResponse.class));
    }

    public Recruiter registerRecruiter(RegisterRequest request){
        RecruiterRequest recruiterRequest = mapper.map(request, RecruiterRequest.class);
        RecruiterResponse response = recruiterService.create(recruiterRequest)
                .orElseThrow(() -> new SomethingWentWrongException("Something went wrong while creating recruiter"));
        return mapper.map(response, Recruiter.class);
    }

    public String generateAndSaveVerificationCode(User user) {
        // generate verification code and store it
        Optional<VerificationCodeResponse> code = verificationCodeService.generateCode(user.getEmail());
        assert code.isPresent();
        VerificationCodeResponse verificationCode = verificationCodeService.save(user, code.get())
                .orElseThrow(() -> new RuntimeException("Something went wrong"));
        return verificationCode.getCode();
    }

    public void sendVerificationCodeViaEmail(String email, String code) {
        emailService.send(email, "MyRH account Verification Code", "Here is Your Verification code: `" + code + "` it will last only for 3 minutes.");
    }
}
