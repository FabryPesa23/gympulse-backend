package fabriziopesaresi.CapstoneProject_GymPulse.service;

import fabriziopesaresi.CapstoneProject_GymPulse.dto.request.LoginRequest;
import fabriziopesaresi.CapstoneProject_GymPulse.dto.request.RegisterRequest;
import fabriziopesaresi.CapstoneProject_GymPulse.dto.response.AuthResponse;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.User;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.UserRepository;
import fabriziopesaresi.CapstoneProject_GymPulse.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email già registrata");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(User.Role.USER);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail(),
                user.getFirstName(), user.getLastName(),
                user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail(),
                user.getFirstName(), user.getLastName(),
                user.getRole().name());
    }
}