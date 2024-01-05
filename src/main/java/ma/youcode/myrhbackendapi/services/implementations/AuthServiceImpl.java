//package ma.youcode.myrhbackendapi.services.implementations;
//
//import com.nimbusds.jwt.JWT;
//import lombok.RequiredArgsConstructor;
//import ma.youcode.myrhbackendapi.dto.requests.RecruiterRequest;
//import ma.youcode.myrhbackendapi.dto.responses.AuthResponse;
//import ma.youcode.myrhbackendapi.entities.Recruiter;
//import ma.youcode.myrhbackendapi.exceptions.ResourceAlreadyExistException;
//import ma.youcode.myrhbackendapi.services.AuthService;
//import ma.youcode.myrhbackendapi.services.RecruiterService;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//import org.springframework.security.oauth2.jwt.*;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Optional;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class AuthServiceImpl implements AuthService {
//
//    private final RecruiterService recruiterService;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtEncoder jwtEncoder;
//    private final AuthenticationManager authenticationManager;
//
//    @Override
//    public Optional<AuthResponse> login(RecruiterRequest recruiterRequest) {
//        Logger.getLogger(getClass().getName()).log(Level.SEVERE, recruiterRequest.getPassword());
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        recruiterRequest.getEmail(),
//                        recruiterRequest.getPassword()
//                )
//        );
//        Recruiter recruiter = recruiterService.findRecruiterByEmail(recruiterRequest.getEmail());
//        Instant instant = Instant.now();
//        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
//        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
//                .issuedAt(instant)
//                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
//                .subject(recruiter.getFullName())
//                .claim("scope", scope)
//                .build();
//        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
//                JwsHeader.with(MacAlgorithm.HS512).build(),
//                jwtClaimsSet
//        );
//        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
//        AuthResponse authResponse = AuthResponse.builder()
//                .token(jwt)
//                .build();
//        return Optional.of(authResponse);
//    }
//
//    @Override
//    public Optional<AuthResponse> register(RecruiterRequest recruiterRequest) {
//        AuthResponse authResponse = AuthResponse.builder()
//                .token("akhna")
//                .build();
//        return Optional.of(authResponse);
//    }
//}
