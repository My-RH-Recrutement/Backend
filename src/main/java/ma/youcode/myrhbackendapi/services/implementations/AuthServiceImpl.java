package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.RegisterRequest;
import ma.youcode.myrhbackendapi.dto.requests.UserRequest;
import ma.youcode.myrhbackendapi.dto.responses.AuthResponse;
import ma.youcode.myrhbackendapi.entities.User;
import ma.youcode.myrhbackendapi.exceptions.ResourceAlreadyExistException;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.repositories.UserRepository;
import ma.youcode.myrhbackendapi.security.jwt.JwtService;
import ma.youcode.myrhbackendapi.services.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
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
//        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User userToSave = mapper.map(userRequest, User.class);
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        User savedUser = userRepository.save(userToSave);
        Authentication authentication = authenticateUser(userRequest.getEmail(), userRequest.getPassword());
        String jwt = jwtService.generateToken(authentication, savedUser);
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
}
