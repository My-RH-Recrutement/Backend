package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.requests.RegisterRequest;
import ma.youcode.myrhbackendapi.dto.requests.UserRequest;
import ma.youcode.myrhbackendapi.dto.responses.AuthResponse;

import java.util.Optional;

public interface AuthService {
    public Optional<AuthResponse> login(UserRequest userRequest);
    public Optional<AuthResponse> register(RegisterRequest userRequest);
}
