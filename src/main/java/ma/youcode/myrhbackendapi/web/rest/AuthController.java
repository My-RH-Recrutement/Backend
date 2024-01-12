package ma.youcode.myrhbackendapi.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.RegisterRequest;
import ma.youcode.myrhbackendapi.dto.requests.UserRequest;
import ma.youcode.myrhbackendapi.dto.requests.VerificationCodeRequest;
import ma.youcode.myrhbackendapi.dto.responses.AuthResponse;
import ma.youcode.myrhbackendapi.dto.responses.UserResponse;
import ma.youcode.myrhbackendapi.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authentication(@Valid @ModelAttribute UserRequest request) {
        Optional<AuthResponse> response = authService.login(request);
        assert response.isPresent();
        return new ResponseEntity<>(response.get(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @ModelAttribute RegisterRequest request) {
        Optional<AuthResponse> response = authService.register(request);
        assert response.isPresent();
        return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
    }

    @PostMapping("/verify-account")
    public ResponseEntity<UserResponse> verifyAccount(@Valid @RequestBody VerificationCodeRequest request) {
        Optional<UserResponse> response = authService.verifyAccount(request);
        assert response.isPresent();
        return new ResponseEntity<>(response.get(), HttpStatus.OK);
    }
}
